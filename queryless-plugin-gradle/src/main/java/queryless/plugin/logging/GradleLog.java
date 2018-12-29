package queryless.plugin.logging;

import org.gradle.api.logging.Logger;

import queryless.core.logging.Log;

public class GradleLog implements Log {

    private final Logger log;

    public GradleLog(final Logger log) {
        this.log = log;
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void debug(final String msg) {
        log.debug(msg);
    }

    @Override
    public void debug(final String msg, final Throwable t) {
        log.debug(msg, t);
    }

    @Override
    public void debug(final Throwable t) {
        log.debug("", t);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public void info(final String msg) {
        log.info(msg);
    }

    @Override
    public void info(final String msg, final Throwable t) {
        log.info(msg, t);
    }

    @Override
    public void info(final Throwable t) {
        log.info("", t);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public void warn(final String msg) {
        log.warn(msg);
    }

    @Override
    public void warn(final String msg, final Throwable t) {
        log.warn(msg, t);
    }

    @Override
    public void warn(final Throwable t) {
        log.warn("", t);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public void error(final String msg) {
        log.error(msg);
    }

    @Override
    public void error(final String msg, final Throwable t) {
        log.error(msg, t);
    }

    @Override
    public void error(final Throwable t) {
        log.error("", t);
    }

}
