package com.thezeroer.exercise.android.curriculumdesign.core;

import android.app.Application;

import com.thezeroer.exercise.android.curriculumdesign.core.nexalithic.BaseNexalithicService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 基础应用
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public abstract class BaseApplication<N extends BaseNexalithicService> extends Application {
    protected static final Logger logger = LoggerFactory.getLogger(BaseApplication.class);
    protected static BaseApplication<?> INSTANCE;
    protected N nexalithicService;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        try {
            nexalithicService = onCreateNexalithicService();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    protected abstract N onCreateNexalithicService() throws IOException;
    @SuppressWarnings("unchecked")
    public static <T extends BaseApplication<?>> T getInstance() {
        return (T) INSTANCE;
    }

    public N getService() {
        return nexalithicService;
    }
}
