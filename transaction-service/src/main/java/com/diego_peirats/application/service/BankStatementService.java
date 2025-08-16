package com.diego_peirats.application.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diego_peirats.domain.entity.Transaction;
import com.diego_peirats.infrastructure.client.EmailClient;
import com.diego_peirats.infrastructure.client.UserClient;
import com.diego_peirats.infrastructure.repository.TransactionRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import email.EmailDetails;
import lombok.extern.slf4j.Slf4j;
import transaction.TransactionDto;
import user.EnquiryRequest;
import user.UserDto;

import static com.diego_peirats.application.utils.PdfUtils.*;

@Service
@Slf4j
public class BankStatementService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private EmailClient emailClient;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final String FILE = "BankStatement.pdf";

	public List<TransactionDto> generateStatement(String accountNumber, LocalDate startDate, LocalDate endDate) throws FileNotFoundException, DocumentException{
		List<TransactionDto> transas =  transactionRepository.findAll()
				.stream()
				.filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
				.filter(transaction -> !transaction.getCreatedAt().isBefore(startDate) && !transaction.getCreatedAt().isAfter(endDate))
				.map(transaction -> modelMapper.map(transaction, TransactionDto.class))
				.collect(Collectors.toList());
		UserDto user = userClient.getUserByAccountNumber(new EnquiryRequest(null, accountNumber));
		
		Rectangle statementSize = new Rectangle(PageSize.A4);
		Document document = new Document(statementSize);
		log.info("Setting document size");
		FileOutputStream outPutStream = new FileOutputStream(FILE);
		PdfWriter.getInstance(document, outPutStream);
		document.open();
		
		List<PdfPCell> bankInfo = List.of(
				createCell("Diego Peirats", BaseColor.BLUE, 20f),
				createCell("San Mateo 58, 03013, Alicante, España", BaseColor.BLUE, 0f));
		
		PdfPTable bankInfoTable = addCellsToTable(bankInfo, 1);
		
		List<PdfPCell> statements = List.of(
				createCell("Start Date: " + startDate, BaseColor.WHITE, 0f),
				createCell("STATEMENT OF ACCOUNT", BaseColor.WHITE, 0f),
				createCell("End Date: " + endDate, BaseColor.WHITE, 0f),
				createCell("Customer name: " + user.getFirstName() + " " + user.getLastName(), BaseColor.WHITE, 0f),
				createCell("", BaseColor.WHITE, 0f),
				createCell("Customer Address " + user.getAddress(), BaseColor.WHITE, 0f));
		
		PdfPTable statementInfo = addCellsToTable(statements, 2);
		
		List<PdfPCell> transactions = List.of(
				createCell("DATE", BaseColor.BLUE, 0f),
				createCell("TRANSACTION TYPE", BaseColor.WHITE, 0f),
				createCell("TRANSACTION AMOUNT", BaseColor.BLUE, 0f),
				createCell("STATUS", BaseColor.BLUE, 0f));
		
		PdfPTable transactionsTable = addCellsToTable(transactions, 4);
		
		transas.forEach(
				transaction -> {
					transactionsTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
					transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
					transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
					transactionsTable.addCell(new Phrase(transaction.getStatus()));
				});
		
		document.add(bankInfoTable);
		document.add(statementInfo);
		document.add(transactionsTable);
		
		document.close();
		
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(user.getEmail())
				.subject("STATEMENT OF ACCOUNT")
				.messageBody("Kindly find your requested account statement attached")
				.attachment(FILE)
				.build();
		
		emailClient.attachmentMail(emailDetails);

		return transas;
	}
}
