package at.htlpinkafeld.RMA_backend_java.exception;

public class DAOSysException extends Exception {
    public DAOSysException(String message) {
        super(message);
    }
    public DAOSysException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public static final int UNIQUE_ERROR = 1062;

    private int errorCode=-1;

    public int getErrorCode() {
        return errorCode;
    }
}