package com.thezeroer.exercise.android.curriculumdesign.core.log;

import android.util.Log;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.AbstractLogger;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class MySlf4jServiceProvider implements SLF4JServiceProvider {
    private ILoggerFactory loggerFactory;
    private IMarkerFactory markerFactory;
    private MDCAdapter mdcAdapter;

    @Override
    public void initialize() {
        markerFactory = new BasicMarkerFactory();
        mdcAdapter = new NOPMDCAdapter();
        loggerFactory = AndroidLogger::new;
    }

    @Override public ILoggerFactory getLoggerFactory() { return loggerFactory; }
    @Override public IMarkerFactory getMarkerFactory() { return markerFactory; }
    @Override public MDCAdapter getMDCAdapter() { return mdcAdapter; }
    @Override public String getRequestedApiVersion() { return "2.0.99"; }
    private static class AndroidLogger extends AbstractLogger {
        AndroidLogger(String name) { this.name = name; }

        @Override public boolean isTraceEnabled() { return true; }
        @Override public boolean isDebugEnabled() { return true; }
        @Override public boolean isInfoEnabled() { return true; }
        @Override public boolean isWarnEnabled() { return true; }
        @Override public boolean isErrorEnabled() { return true; }

        @Override
        public boolean isTraceEnabled(Marker marker) { return isTraceEnabled(); }
        @Override
        public boolean isDebugEnabled(Marker marker) { return isDebugEnabled(); }
        @Override
        public boolean isInfoEnabled(Marker marker) { return isInfoEnabled(); }
        @Override
        public boolean isWarnEnabled(Marker marker) { return isWarnEnabled(); }
        @Override
        public boolean isErrorEnabled(Marker marker) { return isErrorEnabled(); }

        @Override
        protected void handleNormalizedLoggingCall(Level level, Marker marker, String msg, Object[] arguments, Throwable throwable) {
            String formattedMsg = MessageFormatter.arrayFormat(msg, arguments).getMessage();
            switch (level) {
                case TRACE -> Log.v(name, formattedMsg, throwable);
                case DEBUG -> Log.d(name, formattedMsg, throwable);
                case INFO  -> Log.i(name, formattedMsg, throwable);
                case WARN  -> Log.w(name, formattedMsg, throwable);
                case ERROR -> Log.e(name, formattedMsg, throwable);
            }
        }

        @Override protected String getFullyQualifiedCallerName() { return null; }

    }
}