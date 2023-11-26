package com.example.insta.email;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// Class Purpose?
// --------------------------------------------------------------------------------------------
// `JavaEMailSenderImpl` is only created if the `JavaMailSender` _IS_ present in the spring context.
// `JavaMailSender` is only present if it's properties are set in `application.properties`.
// We create this class to send real emails to an SMTP server.
// See also: LoggerEMailSenderImpl.java

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Service to mark this class as a Spring service
// @ConditionalOnBean(...) to only create this bean if `JavaMailSender` is present
// @RequiredArgsConstructor to inject JavaMailSender into this class

@Service
@ConditionalOnBean(JavaMailSender.class)
@RequiredArgsConstructor
public class JavaEMailSenderImpl implements EMailSender {
  public static final String LOG_EMAIL = "Sending email with JavaMailSenderImpl to \nRECIPIENT: {}";

  private final Logger LOGGER = LoggerFactory.getLogger(JavaEMailSenderImpl.class);

  // Suppressing IntelliJ inspection warning; this is a false positive.
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  private final JavaMailSender mailSender;

  @Override
  public void sendMail(EmailDTO emailDTO) {
    try {
      LOGGER.info(LOG_EMAIL, emailDTO.recipient());

      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(emailDTO.recipient());
      message.setSubject(emailDTO.subject());
      message.setText(emailDTO.body());

      mailSender.send(message);
    } catch (MailException e) {
      throw new RuntimeException("Failed to send email", e);
    }
  }
}
