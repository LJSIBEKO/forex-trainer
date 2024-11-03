package forex.trainer.ac.za.utils;

import forex.trainer.ac.za.model.account.confirmation.AccountConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Component
public class EmailUtil
{
    @Autowired
    private  JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    /**
     * Sends a confirmation email with the given account confirmation details.
     *
     * @param accountConfirmation the account confirmation object containing the details.
     */
    public void sendConfirmationWEmail(AccountConfirmation accountConfirmation) {
        String to = accountConfirmation.getAccount().getEmail();
        String clientName = accountConfirmation.getAccount().getUsername();
        String subject = "Account Confirmation";
        String confirmationCode = accountConfirmation.getConfirmationCode();
        String confirmationUrl = "https://example.com/confirm?code=" + confirmationCode; // Change this to your actual confirmation URL

        // Prepare the email content using Thymeleaf
        String htmlContent = generateHtmlContent(clientName, confirmationCode, confirmationUrl);

        // Send the email
        sendHtmlEmail(to, subject, htmlContent);
    }

    /**
     * Sends an HTML email with the given details.
     *
     * @param to      the recipient email address.
     * @param subject the subject of the email.
     * @param htmlContent the body of the email in HTML format.
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indicates that the content is HTML
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the HTML content for the confirmation email using Thymeleaf.
     *
     * @param clientName     the name of the client.
     * @param confirmationCode the confirmation code.
     * @param confirmationUrl the URL for account confirmation.
     * @return the HTML formatted string.
     */
    private String generateHtmlContent(String clientName, String confirmationCode, String confirmationUrl) {
        Context context = new Context();
        context.setVariable("clientName", clientName);
        context.setVariable("confirmationCode", confirmationCode);
        context.setVariable("confirmationUrl", confirmationUrl);

        return templateEngine.process("confirmation", context); // Adjust path as necessary
    }
}
