package com.example.insta.email;

// This interface is implemented by JavaMailSenderImpl and LoggerMailSenderImpl.
public interface EMailSender {
  void sendMail(EmailDTO emailDTO);
}
