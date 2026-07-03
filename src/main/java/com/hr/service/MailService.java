package com.hr.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hr.entity.Mail;
import com.hr.enums.UserRole;
import com.hr.repository.MailRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	private final MailRepository mailRepository;
	private final JavaMailSender javaMailSender;
	
	@Autowired
	private MailService(MailRepository mailRepository, JavaMailSender javaMailSender) {
		super();
		this.mailRepository = mailRepository;
		this.javaMailSender = javaMailSender;
	}
	
	public void sendAndLogEmail(String to, String name, UserRole role) {

	    Mail mail = new Mail();
	    mail.setName(name);
	    mail.setSender("nkute611@gmail.com");
	    mail.setRecipient(to);
	    mail.setRole(role);
	    mail.setSentAt(LocalDateTime.now());

	    try {

	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

	        helper.setFrom("yourcompany@gmail.com");
	        helper.setTo(to);

	        String subject;
	        String html;

	        if (role == UserRole.ADMIN) {

	            subject = "Welcome Administrator - HR Management System";

	            html = """
	                    <!DOCTYPE html>
	                    <html>
	                    <body style="margin:0;padding:30px;background:#f4f6f9;font-family:Arial,sans-serif;">

	                    <div style="max-width:650px;margin:auto;background:#fff;border-radius:10px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,.15);">

	                        <div style="background:#0d6efd;color:white;padding:20px;text-align:center;">
	                            <h2>Welcome Administrator</h2>
	                        </div>

	                        <div style="padding:30px;">

	                            <h3>Hello %s,</h3>

	                            <p>Your <b>Administrator</b> account has been created successfully.</p>

	                            <p>You can now manage:</p>

	                            <ul>
	                                <li>Users</li>
	                                <li>Departments</li>
	                                <li>Managers & Employees</li>
	                                <li>Reports & Analytics</li>
	                            </ul>

	                            <p>Thank you for leading the organization.</p>

	                            <br>

	                            <b>HR Management Team</b>

	                        </div>

	                    </div>

	                    </body>
	                    </html>
	                    """.formatted(name);

	        } else if (role == UserRole.MANAGER) {

	            subject = "Welcome Manager - HR Management System";

	            html = """
	                    <!DOCTYPE html>
	                    <html>
	                    <body style="margin:0;padding:30px;background:#eef8ff;font-family:Arial,sans-serif;">

	                    <div style="max-width:650px;margin:auto;background:white;border-radius:10px;overflow:hidden;">

	                        <div style="background:#198754;color:white;padding:20px;text-align:center;">
	                            <h2>Welcome Manager</h2>
	                        </div>

	                        <div style="padding:30px;">

	                            <h3>Hello %s,</h3>

	                            <p>Your Manager account is ready.</p>

	                            <p>You can now:</p>

	                            <ul>
	                                <li>Manage Employees</li>
	                                <li>Approve Leave</li>
	                                <li>Track Attendance</li>
	                                <li>Review Performance</li>
	                            </ul>

	                            <p>We wish you success in your leadership journey.</p>

	                            <br>

	                            <b>HR Management Team</b>

	                        </div>

	                    </div>

	                    </body>
	                    </html>
	                    """.formatted(name);

	        } else if (role == UserRole.EMPLOYEE) {

	            subject = "Welcome Employee - HR Management System";

	            html = """
	                    <!DOCTYPE html>
	                    <html>
	                    <body style="margin:0;padding:30px;background:#fff7ea;font-family:Arial,sans-serif;">

	                    <div style="max-width:650px;margin:auto;background:white;border-radius:10px;overflow:hidden;">

	                        <div style="background:#ff9800;color:white;padding:20px;text-align:center;">
	                            <h2>Welcome Employee</h2>
	                        </div>

	                        <div style="padding:30px;">

	                            <h3>Hello %s,</h3>

	                            <p>Welcome to our organization.</p>

	                            <p>Your employee account has been created successfully.</p>

	                            <p>You can access:</p>

	                            <ul>
	                                <li>Your Dashboard</li>
	                                <li>Attendance</li>
	                                <li>Leave Portal</li>
	                                <li>Payroll</li>
	                            </ul>

	                            <p>We wish you a wonderful career with us.</p>

	                            <br>

	                            <b>HR Management Team</b>

	                        </div>

	                    </div>

	                    </body>
	                    </html>
	                    """.formatted(name);

	        } else {

	            subject = "Welcome to HR Management System";

	            html = """
	                    <!DOCTYPE html>
	                    <html>
	                    <body style="margin:0;padding:30px;background:#f5f5f5;font-family:Arial,sans-serif;">

	                    <div style="max-width:650px;margin:auto;background:white;border-radius:10px;padding:40px;text-align:center;">

	                        <h2 style="color:#444;">Welcome %s!</h2>

	                        <p>Thank you for registering with our HR Management System.</p>

	                        <p>Your registration has been received successfully.</p>

	                        <p>Our team will contact you shortly if any further action is required.</p>

	                        <br>

	                        <b>HR Management Team</b>

	                    </div>

	                    </body>
	                    </html>
	                    """.formatted(name);

	        }

	        mail.setSubject(subject);

	        helper.setSubject(subject);
	        helper.setText(html, true);

	        javaMailSender.send(mimeMessage);

	        mail.setSuccess(true);

	    } catch (Exception e) {

	        mail.setSuccess(false);
	        System.out.println("Mail Error: " + e.getMessage());

	    }

	    mailRepository.save(mail);
	}
	
	
}
