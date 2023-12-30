package com.example.insta.email;

import com.example.insta.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Class Purpose?
// --------------------------------------------------------------------------------------------
// This class uses our EMailSender interface to send emails.
// The EMailSender interface is implemented by `JavaMailSenderImpl` and `LoggerMailSenderImpl`.
// So this class has no knowledge which concrete implementation is used.

// Strategy pattern
// --------------------------------------------------------------------------------------------
// This is a so-called "strategy pattern".
// The strategy pattern is a behavioral design pattern that selects an algorithm at runtime.
// In our case we select the algorithm to send emails at runtime.
// If the `JavaMailSender` is present, we use the `JavaMailSenderImpl` to send emails,
// otherwise we use the `LoggerMailSenderImpl` to log the emails.

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Service to mark this class as a Spring service
// @RequiredArgsConstructor Lombok annotation that generates a constructor with all required fields.

@Service
@RequiredArgsConstructor
public class EmailService {
  private final EMailSender mailSender;

  // private final JavaMailSenderImpl mailSender;
  // private final LoggerMailSenderImpl mailSender;

  // The protocol of the server where the application is running.
  @Value("${app.public.protocol:http}")
  private String protocol;

  // The domain name of the server where the application is running.
  @Value("${app.public.domain:localhost}")
  private String domain;

  // The port of the server where the application is running.
  @Value("${app.public.port:8080}")
  private String port;

  // The verification link that is sent to the user which he has to click to verify his email.
  private final String VERIFICATION_LINK = "%s://%s:%s/api/registration/token?userId=%s&tokenId=%s";

  // Send Verification Email
  // --------------------------------------------------------------------------------------------
  // @Async
  // public void sendVerificationEmail(User user, String tokenId) {
  public void sendVerificationEmail(User user) {
    // We build the EmailDTO object and send it to the mailSender.
    var receiver = user.getAccount().getVerificationEmail();
    var subject = getVerificationEmailSubject();
    var body = getVerificationEmailBody(user);
    mailSender.sendMail(new EmailDTO(receiver, subject, body));
  }

  public String getVerificationEmailSubject() {
    return "Please verify your email";
  }

  public String getVerificationEmailBody(User user) {
    String token = user.getAccount().getVerificationEmailToken();
    return String.format(VERIFICATION_LINK, protocol, domain, port, user.getId(), token);
  }
}
