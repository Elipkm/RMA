package at.htlpinkafeld.RMA_backend_java.exception;

public class DaoNotFoundException extends Exception{
    private String message;

    public DaoNotFoundException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
