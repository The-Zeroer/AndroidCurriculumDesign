package com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 查看模型助手
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class ViewModelHelper {

    /**
     * 提取泛型 Class
     * @param obj 目标对象 (Activity 或 Fragment)
     * @param index 泛型索引 (0 代表第一个泛型，1 代表第二个)
     */
    @SuppressWarnings("unchecked")
    public static <VM extends BaseViewModel> Class<VM> getViewModelClass(Object obj, int index) {
        Type type = obj.getClass().getGenericSuperclass();
        while (!(type instanceof ParameterizedType)) {
            if (type instanceof Class) {
                type = ((Class<?>) type).getGenericSuperclass();
            } else {
                throw new RuntimeException("未能找到泛型声明: " + obj.getClass().getSimpleName());
            }
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length <= index) {
            throw new RuntimeException("泛型索引超出范围: " + index);
        }
        return (Class<VM>) actualTypeArguments[index];
    }

    /**
     * 统一的实例化逻辑
     */
    public static <VM extends BaseViewModel> VM createViewModel(@NonNull ViewModelStoreOwner owner, @NonNull Class<VM> clazz) {
        if (clazz == NoViewModel.class) {
            return null;
        }
        return new ViewModelProvider(owner, AppInjector.getFactory()).get(clazz);
    }
}