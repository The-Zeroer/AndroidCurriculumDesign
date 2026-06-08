package com.thezeroer.exercise.android.curriculumdesign.user.data.remote;

import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.HandlerPath;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.ConversationRepository;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.MessageRepository;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.MainActivity;
import com.thezeroer.nexalithic.client.NexalithicClient;
import com.thezeroer.nexalithic.core.messaging.handler.HandlerRegistry;
import com.thezeroer.nexalithic.core.messaging.handler.NexalithicHandler;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

import java.io.IOException;
import java.util.List;

/**
 * 用户Nexalithic服务
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class UserNetworkService extends BaseNetworkService {
    public UserNetworkService() throws IOException {
    }

    @Override
    protected NexalithicClient.Builder onInitNexalithicClient(NexalithicClient.Builder nexalithicClientBuilder) throws IOException {
        return nexalithicClientBuilder.registerHandler(new HandlerRegistry.PathMatcher().addDepth(HandlerPath.Message_Push), new NexalithicHandler<>(context -> {
            BusinessPacket request = context.getRequest();
            if (!(request.firstPayload() instanceof TextPayload textPayload)) {
                return;
            }
            List<String> params = TextConverter.toList(textPayload.value());
            if (params.size() != 2) {
                return;
            }
            long messageId = Long.parseLong(params.get(0));
            String senderId = params.get(1);

            // 2. 解析第二个 Payload (真实的文本消息内容)
            String content = "";
            if (request.payloads().get(1) instanceof TextPayload contentPayload) {
                content = contentPayload.value();
            } else {
                // 如果后续拓展了 FilePayload 也可以在这里通过 instance of 转换扩展
                return;
            }

            // 提取当前正在使用App登录的机主账号ID作为 ownerId
            String currentOwnerId = MainActivity.account != null ? MainActivity.account.getAccountId() : "";
            ConversationRepository conversationRepository = new ConversationRepository();
            // 传入三个参数：(谁收到的, 谁发来的, 消息正文)
            conversationRepository.saveIncomingSession(currentOwnerId, senderId, content);

            // 3. 闭环投递：将完整的消息路由到正在活跃的聊天页面中去
            MessageRepository.dispatchLiveMessage(messageId, senderId, content);
        }));
    }
}
