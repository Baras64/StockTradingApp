package Email;

import GUI.AlertMessages;
import com.sun.mail.smtp.SMTPMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.*;

public class OtpGeneration implements Email {
    private static int[] OTPList = {168715, 145784, 125364, 474145, 414156, 741459, 142512};

    public static boolean sendAuthentication(String to, int OTP) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "805");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            SMTPMessage message = new SMTPMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Account Recovery");
            message.setText("Your OTP is " + OTP);
            message.setNotifyOptions(SMTPMessage.NOTIFY_SUCCESS);
            int returnOption = message.getReturnOption();
            System.out.println(returnOption);
            Transport.send(message);
            System.out.println("sent");
            return true;

        } catch (NullPointerException e) {
            System.out.println("Invalid Email ID");
            return false;
        } catch (MessagingException e) {
            AlertMessages.getAlert("Couldn't send email");
            System.exit(1);
        }
        return false;
    }

    public static int getOTP() throws IllegalArgumentException {
        return OTPList[(int) (Math.random() * 7)];
    }

    public static void ifExists(int OTP) throws IllegalArgumentException {
        if (Arrays.binarySearch(OTPList, OTP) == -1)
            throw new IllegalArgumentException("Invalid OTP");
    }
}
