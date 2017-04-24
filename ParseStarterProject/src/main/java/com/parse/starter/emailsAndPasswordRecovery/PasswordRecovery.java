package com.parse.starter.emailsAndPasswordRecovery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * To recover password for a user
 */

public class PasswordRecovery {

    private long code;
    private String codeString;
    private String subject;
    private String email;
    private Session session;
    private ProgressDialog pDialog;
    private Context context;

    public PasswordRecovery(Context context, String email) {
        this.context = context;
        this.email = email;
    }

    public void sendVerificationCode() {
        Random randomCodeGenerator = new Random();
        code = randomCodeGenerator.nextInt(9000) + 1000;

        codeString = String.valueOf(code);
        subject = "Your code for password recovery";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sitpassrec@gmail.com", "sitsiliguri");
            }
        });

        pDialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetrieveFeedTask task = new RetrieveFeedTask();
        task.execute();

    }

    private class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("sitpassrec@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject(subject);
                message.setContent(codeString, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            email = "";
            codeString = "";
            subject = "";
            Toast.makeText(context, "Message Sent...", Toast.LENGTH_SHORT).show();
        }
    }

    public long getCode() {
        return code;
    }

}
