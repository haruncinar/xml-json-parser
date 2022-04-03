package exception;

public class TechnicalException extends RuntimeException{

    public TechnicalException(String errorMessage)
    {
        super(errorMessage);
    }
}
