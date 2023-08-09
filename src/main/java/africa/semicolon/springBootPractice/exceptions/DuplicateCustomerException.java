package africa.semicolon.springBootPractice.exceptions;

public class DuplicateCustomerException extends Exception{

    public DuplicateCustomerException(String message){
        super(message);
    }
}
