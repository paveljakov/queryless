package queryless.core.logging;

public interface Log {

    boolean isDebugEnabled();

    void debug(String msg);

    void debug(String msg, Throwable t);

    void debug(Throwable t);

    boolean isInfoEnabled();

    void info(String msg);

    void info(String msg, Throwable t);

    void info(Throwable t);

    boolean isWarnEnabled();

    void warn(String msg);

    void warn(String msg, Throwable t);

    void warn(Throwable t);

    boolean isErrorEnabled();

    void error(String msg);

    void error(String msg, Throwable t);

    void error(Throwable t);

}
