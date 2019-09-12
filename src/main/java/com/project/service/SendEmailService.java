package com.project.service;

import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.dao.ListEmailManagementDao;
import com.project.dao.ListForSendEmailDao;
import com.project.dao.UserAccountDao;
import com.project.model.ListEmailManagement;
import com.project.model.SendEmailPayroll;
import com.project.model.enumerated.UserRole;

@Service("SendEmailService")
public class SendEmailService {

	@Autowired
	ListEmailManagementDao emailManagementService;

	@Autowired
	UserAccountDao userDao;

	@Autowired
	ListForSendEmailDao sendEmailDao;

	@Autowired
	JReportService jReportService;

	@Autowired
	public JavaMailSender emailSender;

	@Scheduled(cron = " 0 0 7 1 * ?")
	public void SendEmailManagement() {

		MimeMessage message = emailSender.createMimeMessage();
		try {
			List<ListEmailManagement> emailManagement = emailManagementService.getAll();
			for (ListEmailManagement sendEmail : emailManagement) {
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(sendEmail.getEmailName());
				helper.setSubject("Report");
				helper.setText("Report For Management");
//				List;
//				List<SendEmailPayroll> listPS = sendEmailDao.getListPayroll();

				FileSystemResource file = new FileSystemResource(
						"/home/asroful/eclipse-workspace/LinovScheduleMark/coba_client.html");
				FileSystemResource file1 = new FileSystemResource(
						"/home/asroful/eclipse-workspace/LinovScheduleMark/coba_payroll.html");
				helper.addAttachment(file.getFilename(), file);
				helper.addAttachment(file1.getFilename(), file1);

			}
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		emailSender.send(message);
	}

	@Scheduled(cron = " 0 0 7 * * ?")
	public void SendEmailPayroll() {
		Calendar cal = Calendar.getInstance();
		int tanggal = cal.get(Calendar.DATE);

		MimeMessage message = emailSender.createMimeMessage();

		try {
			List<SendEmailPayroll> listPS = sendEmailDao.getListPayroll();
			for (SendEmailPayroll Ps : listPS) {
				if (tanggal == Ps.getStartDate() || tanggal == Ps.getFinishDate() || tanggal == Ps.getFinishDate() - 1
						|| tanggal == Ps.getFinishDate() - 2) {
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setTo(Ps.getEmail());
					helper.setSubject("Payroll Notification");
					helper.setText("Notifikasi For Payroll Spesialist ");
					jReportService.getNotifPayroll(Ps.getUserCode());

					FileSystemResource file = new FileSystemResource(
							"/home/asroful/eclipse-workspace/LinovScheduleMark/notif_payroll.html");
					helper.addAttachment(file.getFilename(), file);
				}
			}
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		emailSender.send(message);
	}

	public void SendNotifNewAccount(String userName,String email, UserRole userRole) {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email);
			helper.setSubject("Notification New User");
			helper.setText("Dear "+userName +",\n\nEmail Anda ("+email+") telah terdaftar sebagai "+userRole+" dalam aplikasi Linov Schedule Mark.\n\nTerima Kasih");

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}

		emailSender.send(message);
	}
}
