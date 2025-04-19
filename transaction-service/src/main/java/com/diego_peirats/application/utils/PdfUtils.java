package com.diego_peirats.application.utils;

import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfUtils {

	
	public static PdfPCell createCell(String phrase, BaseColor color, float padding) {
		PdfPCell cell = new PdfPCell(new Phrase(phrase));
		cell.setBackgroundColor(color);
		cell.setBorder(0);
		cell.setPadding(padding);
		return cell;
	}
	
	public static PdfPTable addCellsToTable(List<PdfPCell> list, int columns) {
		PdfPTable table = new PdfPTable(columns);
		for (PdfPCell cell : list)
			table.addCell(cell);
		return table;
			
	}

}
