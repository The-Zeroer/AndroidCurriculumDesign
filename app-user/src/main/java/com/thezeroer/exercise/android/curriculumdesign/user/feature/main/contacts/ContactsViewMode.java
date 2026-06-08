package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.mode.Account;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.ContactsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人视图模型
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/07
 */
public class ContactsViewMode extends BaseViewModel {

    private final ContactsRepository contactsRepository = new ContactsRepository();

    private final MutableLiveData<List<Account>> contactsListLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> toastLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);

    public LiveData<List<Account>> getContactsList() {
        return contactsListLiveData;
    }

    public LiveData<String> getToastEvent() {
        return toastLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    /**
     * 从服务端异步加载全员联系人
     */
    public void fetchContacts() {
        isLoadingLiveData.postValue(true);
        
        contactsRepository.getAllContacts()
                .thenAccept(resource -> {
                    isLoadingLiveData.postValue(false);
                    if (resource.isSuccess() && resource.getData() != null) {
                        contactsListLiveData.postValue(resource.getData());
                    } else {
                        toastLiveData.postValue("获取联系人失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    isLoadingLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，请刷新重试");
                    return null;
                });
    }
}