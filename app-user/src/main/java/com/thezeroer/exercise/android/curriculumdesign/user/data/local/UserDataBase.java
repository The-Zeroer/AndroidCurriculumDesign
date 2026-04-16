package com.thezeroer.exercise.android.curriculumdesign.user.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.thezeroer.exercise.android.curriculumdesign.core.data.local.BaseDataBase;
import com.thezeroer.exercise.android.curriculumdesign.core.data.local.entity.AuthEntity;

/**
 * 用户数据库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
@Database(entities = {AuthEntity.class}, version = 1, exportSchema = false)
public abstract class UserDataBase extends BaseDataBase {

    private static final String DATABASE_NAME = "curriculumdesign_user_db";
    private static volatile UserDataBase instance;

    /**
     * 获取数据库实例
     */
    public static UserDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (UserDataBase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    UserDataBase.class,
                                    DATABASE_NAME
                            )
                            // 实际项目中建议处理迁移，此处为实验目的允许重建
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
