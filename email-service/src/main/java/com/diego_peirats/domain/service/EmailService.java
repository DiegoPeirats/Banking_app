package com.diego_peirats.domain.service;

import email.EmailDetails;

public interface EmailService {

	void sendEmailAlert(EmailDetails emailDetails);
	void sendEmailWithAttachment(EmailDetails emailDetails);
}