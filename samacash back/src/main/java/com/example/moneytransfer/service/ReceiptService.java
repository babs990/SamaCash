package com.example.moneytransfer.service;

import com.example.moneytransfer.entity.Transaction;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class ReceiptService {

    public byte[] generateReceipt(Transaction transaction) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Reçu de Transaction").setBold().setFontSize(18));
            document.add(new Paragraph("ID Transaction: " + transaction.getId()));
            document.add(new Paragraph("Date: " + transaction.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
            document.add(new Paragraph("Type: " + transaction.getType()));
            document.add(new Paragraph("Montant: " + transaction.getAmount() + " XOF"));
            if (transaction.getFromAccount() != null) document.add(new Paragraph("Expéditeur: Compte #" + transaction.getFromAccount().getId()));
            if (transaction.getToAccount() != null) document.add(new Paragraph("Destinataire: Compte #" + transaction.getToAccount().getId()));
            document.add(new Paragraph("Description: " + transaction.getDescription()));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du reçu PDF", e);
        }
    }
}
