package at.htlpinkafeld.RMA_backend_java.exception;

public class DaoSysException extends Exception {

    public DaoSysException(String message) {
        super(message);
    }

    public DaoSysException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public static final int UNIQUE_ERROR = 1062;

    private int errorCode = -1;

    public int getErrorCode() {
        return errorCode;
    }
}