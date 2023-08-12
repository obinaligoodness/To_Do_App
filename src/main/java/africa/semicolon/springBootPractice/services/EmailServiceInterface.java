package africa.semicolon.springBootPractice.services;

public interface EmailServiceInterface {
    void sendEmail(String toEmail, String subject,String content);

}
