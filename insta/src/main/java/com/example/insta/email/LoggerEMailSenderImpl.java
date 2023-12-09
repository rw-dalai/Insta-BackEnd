package com.example.insta.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// Class Purpose?
// --------------------------------------------------------------------------------------------
// `LoggerEMailSenderImpl` is only created if the `JavaMailSender` is _NOT_ present
// in the spring context.
// `JavaMailSender` is only present if it's properties are set in `application.properties`.
// We create this class to avoid having to configure an SMTP server for development.
// See also: JavaEMailSenderImpl.java

// Annotations used?
// --------------------------------------------------------------------------------------------
// @Service to mark this class as a Spring service
// @ConditionalOnMissingBean(...) to only create this bean if `JavaMailSender` is not present
// @RequiredArgsConstructor Lombok annotation that generates a constructor with all required fields.

@Service
@ConditionalOnMissingBean(JavaMailSender.class)
public class LoggerEMailSenderImpl implements EMailSender {
  private final Logger LOGGER = LoggerFactory.getLogger(LoggerEMailSenderImpl.class);

  public static final String LOG_EMAIL_INFO =
      "Logging email with LoggerMailSenderImpl to \nRECIPIENT: {}";

  public static final String LOG_EMAIL_BODY = "RECIPIENT: {}\nSUBJECT: {}\nBODY: {}";

  @Override
  public void sendMail(EmailDTO emailDTO) {
    LOGGER.info(LOG_EMAIL_INFO, emailDTO.recipient());

    LOGGER.info(LOG_EMAIL_BODY, emailDTO.recipient(), emailDTO.subject(), emailDTO.body());
  }
}
