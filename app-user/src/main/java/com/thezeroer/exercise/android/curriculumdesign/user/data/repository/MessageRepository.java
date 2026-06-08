package com.thezeroer.exercise.android.curriculumdesign.user.data.repository;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.HandlerPath;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.BaseRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.MessageType;
import com.thezeroer.exercise.android.curriculumdesign.user.mode.Message;
import com.thezeroer.exercise.android.curriculumdesign.user.util.MessageUtil;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.AbstractPayload;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 消息仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/06
 */
public class MessageRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    /**
     * 发送消息：服务端仅返回 messageId，本地组装完整 Message 对象
     */
    public CompletableFuture<Resource<Message>> pushMessage(String senderId, String targetId, String content, long timestamp) {
        CompletableFuture<Resource<Message>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.Message_Push).attach(new TextPayload(targetId), new TextPayload(content)))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        Long returnedMessageId = null;

                        // 1. 从响应中解析服务端返回的唯一 messageId
                        for (AbstractPayload<?> payload : response.payloads()) {
                            switch (payload) {
                                case TextPayload textPayload -> {
                                    try {
                                        // 假设服务端直接返回了 id 的字符串形式
                                        returnedMessageId = Long.parseLong(textPayload.value());
                                    } catch (NumberFormatException e) {
                                        // 如果服务端返回的是 List 序列化文本，则用 TextConverter 解析
                                        List<String> list = TextConverter.toList(textPayload.value());
                                        if (list != null && !list.isEmpty()) {
                                            returnedMessageId = Long.parseLong(list.get(0));
                                        }
                                    }
                                }
                                default -> {
                                }
                            }
                        }

                        if (returnedMessageId != null) {
                            // 2. 结合请求参数，在本地拼装出完整的 Message 实体类
                            Message sentMessage = new Message(
                                    returnedMessageId,
                                    MessageUtil.generateConversationId(senderId, targetId), // conversationId (若无特殊业务要求可直接传 null)
                                    senderId,
                                    targetId,
                                    content,
                                    MessageType.TEXT,
                                    timestamp
                            );
                            // 🟢【核心应用点 2】自己发信成功，立刻在本地同步更新/创建对应的会话记录（未读数为 0）
                            new ConversationRepository().saveOutgoingSession(senderId, targetId, content);
                            result.complete(Resource.success(sentMessage));
                        } else {
                            result.complete(Resource.failed("解析返回的 messageId 失败"));
                        }
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        if (future == null) {
            result.complete(Resource.failed("操作失败，请稍后重试"));
        }
        return result;
    }

    public CompletableFuture<Resource<List<Message>>> pullMessage(String targetId, long lastMessageId) {
        CompletableFuture<Resource<List<Message>>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.Message_Pull).attach(new TextPayload(TextConverter.fromArray(targetId, String.valueOf(lastMessageId)))))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        List<Message> messages = new ArrayList<>();
                        if (response.payloads() != null) {
                            for (AbstractPayload<?> payload : response.payloads()) {
                                switch (payload) {
                                    case TextPayload textPayload -> {
                                        List<String> list = TextConverter.toList(textPayload.value());
                                        messages.add(new Message(
                                                Long.parseLong(list.get(0)),
                                                list.get(1),
                                                list.get(2),
                                                list.get(3),
                                                list.get(4),
                                                MessageType.valueOf(Integer.parseInt(list.get(5))),
                                                Long.parseLong(list.get(6)))
                                        );
                                    }
                                    default -> {
                                    }
                                }
                            }
                        }
                        result.complete(Resource.success(messages));
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        if (future == null) {
            result.complete(Resource.failed("操作失败，请稍后重试"));
        }
        return result;
    }

    // 1. 定义全局实时消息监听器接口
    public interface OnLiveMessageReceivedListener {
        void onNewMessage(long messageId, String senderId, String content);
    }

    private static OnLiveMessageReceivedListener liveMessageListener;

    /**
     * 供 ChatViewMode 在进入页面时动态注册监听
     */
    public static void registerLiveListener(OnLiveMessageReceivedListener listener) {
        liveMessageListener = listener;
    }

    /**
     * 供 ChatViewMode 在销毁页面时注销监听，防止内存泄漏
     */
    public static void unregisterLiveListener() {
        liveMessageListener = null;
    }

    /**
     * 供全局 UserNetworkService 拦截到推送时调用分发
     */
    public static void dispatchLiveMessage(long messageId, String senderId, String content) {
        if (liveMessageListener != null) {
            liveMessageListener.onNewMessage(messageId, senderId, content);
        }
    }
}