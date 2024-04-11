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

import com.ericafenyo.seniorhub.api.Tuple;
import com.ericafenyo.seniorhub.model.Mail;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.services.MailService;
import com.ericafenyo.seniorhub.util.Cache;
import com.ericafenyo.seniorhub.spi.TextEncoder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

  private final JavaMailSender mail;
  private final TextEncoder textEncoder;

  public MailServiceImpl(JavaMailSender mail, TextEncoder textEncoder) {
    this.mail = mail;
    this.textEncoder = textEncoder;
  }

  private void send(Mail options) {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(options.getSender());
    message.setTo(options.getRecipient());
    message.setSubject(options.getSubject());
    message.setText(options.getBody());

    mail.send(message);
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

    var report = new Report();
    report.setMessage("Verification code has been sent to the provided email address");
    report.setTime(LocalDateTime.now());

    return new Tuple<>(report, storageKey);
  }

  private int generateVerificationCode() {
    Random random = new Random();
    return 10000 + random.nextInt(90000);
  }
}
