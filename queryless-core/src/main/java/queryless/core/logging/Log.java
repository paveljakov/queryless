package queryless.core.logging;

public interface Log {

    boolean isDebugEnabled();

    void debug(CharSequence msg);

    void debug(CharSequence msg, Throwable t);

    void debug(Throwable t);

    boolean isInfoEnabled();

    void info(CharSequence msg);

    void info(CharSequence msg, Throwable t);

    void info(Throwable t);

    boolean isWarnEnabled();

    void warn(CharSequence msg);

    void warn(CharSequence msg, Throwable t);

    void warn(Throwable t);

    boolean isErrorEnabled();

    void error(CharSequence msg);

    void error(CharSequence msg, Throwable t);

    void error(Throwable t);

}
