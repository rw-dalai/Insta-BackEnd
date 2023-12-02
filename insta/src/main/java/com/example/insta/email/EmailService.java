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
// @RequiredArgsConstructor to inject a bean of type EMailSender into this class

@Service
@RequiredArgsConstructor
public class EmailService {
  private final EMailSender mailSender;

  @Value("${domain}")
  private String domain;

  private final String VERIFICATION_LINK = "%s/api/registration/verify/userId=%s&tokenId=%s";

  //    private final JavaMailSenderImpl mailSender;
  //    private final LoggerMailSenderImpl mailSender;

  //     @Async
  //  public void sendVerificationEmail(User user, String tokenId) {
  public void sendVerificationEmail(User user) {
    var emailDTO =
        new EmailDTO(
            user.getEmail(),
            getVerificationSubject(),
            getVerificationBody(user, user.getAccount().getTokenId()));
    mailSender.sendMail(emailDTO);
  }

  public String getVerificationSubject() {
    return "Please verify your email";
  }

  public String getVerificationBody(User user, String tokenId) {
    return String.format(VERIFICATION_LINK, domain, user.getId(), tokenId);
  }
}
