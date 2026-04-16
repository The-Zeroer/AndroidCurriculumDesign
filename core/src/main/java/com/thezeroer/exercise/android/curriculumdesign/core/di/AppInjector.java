package com.thezeroer.exercise.android.curriculumdesign.core.di;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.thezeroer.exercise.android.curriculumdesign.core.data.local.BaseDataBase;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.BaseRepository;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * App 注入器
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class AppInjector {
    private static volatile BaseDataBase database;
    private static volatile BaseNetworkService networkService;
    private static final Map<Class<?>, BaseRepository> REPO_MAP = new ConcurrentHashMap<>();

    /**
     * 注册Repository对象
     */
    public static void registerRepository(BaseRepository repository) {
        if (repository != null) {
            Log.d("AppInjector", "注册 Repository: " + repository.getClass().getName());
            REPO_MAP.put(repository.getClass(), repository);
        }
    }

    /**
     * 由具体的子 App 模块在 Application 中调用
     * 传入该 App 对应的具体实现类
     */
    public static void setDatabase(BaseDataBase database) {
        AppInjector.database = database;
    }

    /**
     * 获取通用的数据库引用
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseDataBase> T getDatabase() {
        if (database == null) {
            throw new RuntimeException("AppInjector 尚未初始化数据库");
        }
        return (T) database;
    }

    public static void setNetworkService(BaseNetworkService networkService) {
        AppInjector.networkService = networkService;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseNetworkService> T getNetworkService() {
        if (networkService == null) {
            throw new RuntimeException("AppInjector 尚未初始化网络服务");
        }
        return (T) networkService;
    }

    @SuppressWarnings("unchecked")
    public static ViewModelProvider.Factory getFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                Constructor<?>[] constructors = modelClass.getConstructors();
                if (constructors.length == 0) {
                    throw new RuntimeException(modelClass.getName() + " 没有公开的构造函数");
                }
                Constructor<?> constructor = constructors[0];
                Class<?>[] paramTypes = constructor.getParameterTypes();
                Object[] params = new Object[paramTypes.length];
                for (int i = 0; i < paramTypes.length; i++) {
                    Class<?> paramType = paramTypes[i];
                    Object repo = REPO_MAP.get(paramType);
                    if (repo == null) {
                        throw new RuntimeException("找不到依赖类型: " + paramType.getName() + " (用于 " + modelClass.getName() + ")");
                    }
                    params[i] = repo;
                }
                try {
                    return (T) constructor.newInstance(params);
                } catch (Exception e) {
                    throw new RuntimeException("实例化失败: " + modelClass.getName(), e);
                }
            }
        };
    }
}
