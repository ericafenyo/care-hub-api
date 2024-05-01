/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2024 Eric Afenyo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ericafenyo.seniorhub.implementation.services;

import com.ericafenyo.seniorhub.EnvironmentVariables;
import com.ericafenyo.seniorhub.api.Tuple;
import com.ericafenyo.seniorhub.model.Mail;
import com.ericafenyo.seniorhub.model.Mail.Context;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.services.MailService;
import com.ericafenyo.seniorhub.util.Cache;
import com.ericafenyo.seniorhub.spi.TextEncoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DefaultMailService implements MailService {
  public static final String TEMPLATE_INVITATION_EMAIL = "/mails/invitation.html";
  public static final String SUBJECT_INVITATION_EMAIL = "You've been invited to join Senior Hub!";

  private final JavaMailSender javaMailSender;
  private final TextEncoder textEncoder;
  private final Configuration configuration;
  private final EnvironmentVariables environment;


  @Override
  public void send(Mail options) {
    try {
      var message = javaMailSender.createMimeMessage();
      var content = new MimeMessageHelper(message, true, "UTF-8");

      content.setFrom(options.getSender());
      content.setTo(options.getRecipient());
      content.setSubject(options.getSubject());
      content.setText(options.getBody(), true);

      javaMailSender.send(content.getMimeMessage());
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Tuple<Report, String> sendVerificationCode(String email) {
    // Generate a verification code
    var code = generateVerificationCode();

    // Hash the code so that it is not stored on our server
    var encodedText = textEncoder.encode(String.valueOf(code));

    // Cache the encoded verification code
    var storageKey = KeyGenerators.string().generateKey();
    var cache = Cache.builder()
        .duration(Duration.ofMinutes(5))
        .build();

    cache.put(storageKey, encodedText);

    var verificationMail = Mail.builder()
        .sender("no-reply@example.com")
        .recipient(email)
        .subject("Verification code")
        .body("Use this code for verification: %s".formatted(code))
        .build();

    send(verificationMail);

    var report = new Report("Verification code has been sent to the provided email address");

    return new Tuple<>(report, storageKey);
  }

  @Override
  public Report sendInvitation(String email, Context context) {
    // Extract the email template
    var html = extractHTML(TEMPLATE_INVITATION_EMAIL, context);

    // Crate an email request
    var mail = Mail.builder()
        .recipient(email)
        .sender(environment.getMailSender())
        .subject(SUBJECT_INVITATION_EMAIL)
        .body(html)
        .build();

    send(mail);

    return gererateReport();
  }

  private static Report gererateReport() {
    return new Report("Email sent successfully");
  }

  private String extractHTML(String path, Context context) {
    try {
      Template template = configuration.getTemplate(path);
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
    } catch (IOException | TemplateException e) {
      throw new RuntimeException(e);
    }
  }

  private int generateVerificationCode() {
    Random random = new Random();
    return 10000 + random.nextInt(90000);
  }
}
