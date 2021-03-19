package at.htlpinkafeld.RMA_backend_java.exception;

public class DaoResourceAlreadyExistsException extends Exception{
    public DaoResourceAlreadyExistsException(String message){
        super(message);
    }
}
