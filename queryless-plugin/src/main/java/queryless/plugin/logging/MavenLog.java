package queryless.plugin.logging;

import queryless.core.logging.Log;

public class MavenLog implements Log {

    private final org.apache.maven.plugin.logging.Log log;

    public MavenLog(final org.apache.maven.plugin.logging.Log log) {
        this.log = log;
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void debug(final CharSequence msg) {
        log.debug(msg);
    }

    @Override
    public void debug(final CharSequence msg, final Throwable t) {
        log.debug(msg, t);
    }

    @Override
    public void debug(final Throwable t) {
        log.debug(t);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public void info(final CharSequence msg) {
        log.info(msg);
    }

    @Override
    public void info(final CharSequence msg, final Throwable t) {
        log.info(msg, t);
    }

    @Override
    public void info(final Throwable t) {
        log.info(t);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public void warn(final CharSequence msg) {
        log.warn(msg);
    }

    @Override
    public void warn(final CharSequence msg, final Throwable t) {
        log.warn(msg, t);
    }

    @Override
    public void warn(final Throwable t) {
        log.warn(t);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public void error(final CharSequence msg) {
        log.error(msg);
    }

    @Override
    public void error(final CharSequence msg, final Throwable t) {
        log.error(msg, t);
    }

    @Override
    public void error(final Throwable t) {
        log.error(t);
    }

}
