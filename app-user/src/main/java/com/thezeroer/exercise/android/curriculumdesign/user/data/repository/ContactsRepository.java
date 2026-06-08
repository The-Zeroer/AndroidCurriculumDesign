package com.thezeroer.exercise.android.curriculumdesign.user.data.repository;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.BaseRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.HandlerPath;
import com.thezeroer.exercise.android.curriculumdesign.core.mode.Account;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.AbstractPayload;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 联系人/通讯录仓库
 */
public class ContactsRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    // 🟢 核心：内存全局静态缓存，保存 accountId -> accountName 的映射
    private static final Map<String, String> contactNameCache = new ConcurrentHashMap<>();

    /**
     * 【提供给别的组件调用的全局静态功能】
     * 根据账号ID瞬间获取昵称，若缓存未命中则返回兜底名字
     *
     * @param accountId 对方ID
     * @param defaultName 兜底名字（如 "用户 " + id）
     */
    public static String getCachedName(String accountId, String defaultName) {
        if (accountId == null) return defaultName;
        String cachedName = contactNameCache.get(accountId);
        return cachedName != null ? cachedName : defaultName;
    }

    /**
     * 获取所有可公开聊天的联系人列表
     */
    public CompletableFuture<Resource<List<Account>>> getAllContacts() {
        CompletableFuture<Resource<List<Account>>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.Contacts_GetAll))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        List<Account> userList = new ArrayList<>();

                        for (AbstractPayload<?> payload : response.payloads()) {
                            switch (payload) {
                                case TextPayload textPayload -> {
                                    List<String[]> dataList = TextConverter.toListArray(textPayload.value());
                                    if (dataList != null) {
                                        for (String[] data : dataList) {
                                            if (data != null && data.length >= 2) {
                                                Account account = new Account(data[0], data[1]);
                                                userList.add(account);

                                                // 🟢【缓存注入点】拉取成功时，默默存入内存映射表
                                                contactNameCache.put(data[0], data[1]);
                                            }
                                        }
                                    }
                                }
                                default -> {
                                }
                            }
                        }
                        result.complete(Resource.success(userList));
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
}