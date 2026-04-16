package com.thezeroer.exercise.android.curriculumdesign.admin.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.thezeroer.exercise.android.curriculumdesign.core.data.local.BaseDataBase;
import com.thezeroer.exercise.android.curriculumdesign.core.data.local.entity.AuthEntity;

/**
 * 管理员数据库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
@Database(entities = {AuthEntity.class}, version = 1, exportSchema = false)
public abstract class AdminDataBase extends BaseDataBase {

    private static final String DATABASE_NAME = "curriculumdesign_admin_db";
    private static volatile AdminDataBase instance;

    /**
     * 获取数据库实例
     */
    public static AdminDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (AdminDataBase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AdminDataBase.class,
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
