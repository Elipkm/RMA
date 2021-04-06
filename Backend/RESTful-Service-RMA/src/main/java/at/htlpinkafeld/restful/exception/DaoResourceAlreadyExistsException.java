package at.htlpinkafeld.restful.exception;

public class DaoResourceAlreadyExistsException extends Exception{
    public DaoResourceAlreadyExistsException(String message){
        super(message);
    }
}
