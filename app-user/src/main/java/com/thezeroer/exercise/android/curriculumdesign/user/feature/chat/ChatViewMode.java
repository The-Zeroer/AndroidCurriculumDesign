package com.thezeroer.exercise.android.curriculumdesign.user.feature.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.MessageType;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.ConversationRepository;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.MessageRepository;
import com.thezeroer.exercise.android.curriculumdesign.user.mode.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天视图模式
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/06
 */
public class ChatViewMode extends BaseViewModel {

    private final MessageRepository messageRepository = new MessageRepository();

    private final MutableLiveData<List<Message>> messageListLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> toastLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> sendStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRefreshingLiveData = new MutableLiveData<>(false);

    // 新增一个判断量，用来告诉 Activity 这是“首屏第一页”还是“下拉加载更多历史”
    private final MutableLiveData<Boolean> isFirstLoadLiveData = new MutableLiveData<>(true);

    private String currentUserId;
    private String targetId;

    // 正确切换回初始值：获取比当前 ID 更小的历史记录，首次传入极大值
    private long lastMessageId = Long.MAX_VALUE;

    public void init(String currentUserId, String targetId) {
        this.currentUserId = currentUserId;
        this.targetId = targetId;
        // 🟢【核心应用点 3】进入与该用户的聊天页面，立刻调用仓库层清除对应的未读数红点
        new ConversationRepository().clearUnread(currentUserId, targetId);
        // 【方向一：在线接收实时推送】新消息，直接往列表尾部（底部）追加
        MessageRepository.registerLiveListener((messageId, senderId, content) -> {
            if (senderId != null && senderId.equals(this.targetId)) {
                // 💡【附加细节】如果用户已经在当前聊天页内挂着，收到的实时消息不需要累计未读红点
                // 因此在这里可以顺手再清空一下，防止在当前页聊天时后台还在累加未读数
                new ConversationRepository().clearUnread(currentUserId, this.targetId);
                Message liveMessage = new Message(
                        messageId,
                        null,
                        senderId,
                        this.currentUserId,
                        content,
                        MessageType.TEXT,
                        System.currentTimeMillis()
                );

                List<Message> currentList = messageListLiveData.getValue();
                List<Message> updatedList = new ArrayList<>(currentList != null ? currentList : new ArrayList<>());
                updatedList.add(liveMessage); // 追加到末尾

                isFirstLoadLiveData.postValue(true); // 标记为需要滚动到最底部
                messageListLiveData.postValue(updatedList);
            }
        });

        // 首次进入页面，自动拉取第一页（最新30条历史记录）
        pullMessages();
    }

    public LiveData<List<Message>> getMessageList() { return messageListLiveData; }
    public LiveData<String> getToastEvent() { return toastLiveData; }
    public LiveData<Boolean> getSendStatusEvent() { return sendStatusLiveData; }
    public LiveData<Boolean> getIsRefreshing() { return isRefreshingLiveData; }
    public LiveData<Boolean> getIsFirstLoad() { return isFirstLoadLiveData; }

    /**
     * 自己发送文本消息
     */
    public void sendMessage(String content) {
        if (content == null || content.trim().isEmpty()) {
            toastLiveData.setValue("消息内容不能为空");
            return;
        }

        long timestamp = System.currentTimeMillis();
        messageRepository.pushMessage(currentUserId, targetId, content, timestamp)
                .thenAccept(resource -> {
                    if (resource.isSuccess() && resource.getData() != null) {
                        Message sentMessage = resource.getData();

                        // 自己发的消息，也是最新的，直接往列表尾部追加
                        List<Message> currentList = messageListLiveData.getValue();
                        List<Message> updatedList = new ArrayList<>(currentList != null ? currentList : new ArrayList<>());
                        updatedList.add(sentMessage);

                        isFirstLoadLiveData.postValue(true); // 发完信需要滚到底部
                        messageListLiveData.postValue(updatedList);
                        sendStatusLiveData.postValue(true);
                    } else {
                        sendStatusLiveData.postValue(false);
                        toastLiveData.postValue("发送失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    sendStatusLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，请重试");
                    return null;
                });
    }

    /**
     * 【方向二：拉取历史消息】手动刷新（向上翻页）
     */
    public void pullMessages() {
        if (targetId == null) return;

        isRefreshingLiveData.postValue(true);

        messageRepository.pullMessage(targetId, lastMessageId)
                .thenAccept(resource -> {
                    isRefreshingLiveData.postValue(false);

                    if (resource.isSuccess() && resource.getData() != null) {
                        List<Message> remoteMessages = resource.getData();
                        if (!remoteMessages.isEmpty()) {

                            // 【核心改动】因为是 lt 且 Asc 升序，remoteMessages 的第 0 条数据是这批历史记录里 ID 最小、时间最老的
                            // 将其赋值给 lastMessageId，下次下拉刷新才能继续往前翻页
                            long minId = remoteMessages.get(0).getMessageId();
                            if (minId < lastMessageId) {
                                lastMessageId = minId;
                            }

                            List<Message> currentList = messageListLiveData.getValue();
                            List<Message> updatedList = new ArrayList<>();

                            // 💡 算法：历史数据应该按时间线整体拼装在“过去”，所以新查出的更老的数据，要放到 List 的 [0] 索引位置
                            updatedList.addAll(remoteMessages);
                            if (currentList != null) {
                                updatedList.addAll(currentList);
                            }

                            // 如果是第一次拉数据（local 为空），说明是进页面的初次渲染
                            isFirstLoadLiveData.postValue(currentList == null || currentList.isEmpty());
                            messageListLiveData.postValue(updatedList);
                        } else {
                            if (lastMessageId != Long.MAX_VALUE) {
                                toastLiveData.postValue("没有更多历史消息了");
                            }
                        }
                    } else {
                        toastLiveData.postValue("拉取消息失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    isRefreshingLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，无法获取新消息");
                    return null;
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        MessageRepository.unregisterLiveListener();
    }
}