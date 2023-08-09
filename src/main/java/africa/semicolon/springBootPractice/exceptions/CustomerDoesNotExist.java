package africa.semicolon.springBootPractice.exceptions;

public class CustomerDoesNotExist extends Exception{

    public  CustomerDoesNotExist(String message){
        super(message);
    }
}
