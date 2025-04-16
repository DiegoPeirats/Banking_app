package com.diego_peirats.domain.service;

import com.diego_peirats.domain.entity.EmailDetails;

public interface EmailService {

	void sendEmailAlert(EmailDetails emailDetails);
	void sendEmailWithAttachment(EmailDetails emailDetails);
}