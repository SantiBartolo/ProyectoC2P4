package com.universidad.proyecto.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.universidad.proyecto.model.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Utilidad para generar certificados en PDF
 */
public class PDFGeneratorUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
    
    /**
     * Genera un certificado en PDF para un estudiante
     * @param student Estudiante para el cual generar el certificado
     * @return Array de bytes con el contenido del PDF
     * @throws DocumentException Si hay un error al crear el documento
     * @throws IOException Si hay un error de I/O
     */
    public static byte[] generateCertificate(Student student) throws IOException {
        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            
            document.open();
            
            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            Paragraph title = new Paragraph(AppConstants.CERTIFICATE_TITLE, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);
            
            // Contenido
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            
            Paragraph paragraph1 = new Paragraph();
            paragraph1.add(new Chunk("Por medio del presente se certifica que ", contentFont));
            paragraph1.add(new Chunk(student.getFirstName() + " " + student.getLastName(), boldFont));
            paragraph1.add(new Chunk(", identificado(a) con documento número ", contentFont));
            paragraph1.add(new Chunk(student.getDocumentNumber() != null ? student.getDocumentNumber() : "N/A", boldFont));
            paragraph1.add(new Chunk(", está registrado(a) como estudiante en el programa de ", contentFont));
            paragraph1.add(new Chunk(student.getProgram() != null ? student.getProgram() : "N/A", boldFont));
            paragraph1.add(new Chunk(".", contentFont));
            paragraph1.setSpacingAfter(20);
            document.add(paragraph1);
            
            Paragraph paragraph2 = new Paragraph();
            paragraph2.add(new Chunk("Fecha de nacimiento: ", contentFont));
            paragraph2.add(new Chunk(
                student.getDateOfBirth().format(DATE_FORMATTER), 
                boldFont
            ));
            paragraph2.setSpacingAfter(20);
            document.add(paragraph2);
            
            Paragraph paragraph3 = new Paragraph();
            paragraph3.add(new Chunk("Email de contacto: ", contentFont));
            paragraph3.add(new Chunk(student.getEmail(), boldFont));
            paragraph3.setSpacingAfter(30);
            document.add(paragraph3);
            
            // Fecha de emisión
            Paragraph dateParagraph = new Paragraph();
            dateParagraph.add(new Chunk("Fecha de emisión: ", contentFont));
            dateParagraph.add(new Chunk(
                java.time.LocalDate.now().format(DATE_FORMATTER), 
                boldFont
            ));
            dateParagraph.setAlignment(Element.ALIGN_RIGHT);
            dateParagraph.setSpacingBefore(50);
            document.add(dateParagraph);
            
            document.close();
            
            return outputStream.toByteArray();
            
        } catch (DocumentException e) {
            throw new IOException("Error al generar el certificado PDF: " + e.getMessage(), e);
        }
    }
}

