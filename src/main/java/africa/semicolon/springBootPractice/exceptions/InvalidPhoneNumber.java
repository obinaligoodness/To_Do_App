package africa.semicolon.springBootPractice.exceptions;

public class InvalidPhoneNumber extends Exception{
    public InvalidPhoneNumber(String message){
        super(message);
    }
}
