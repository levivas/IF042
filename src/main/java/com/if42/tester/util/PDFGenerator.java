package com.if42.tester.util;

import com.if42.tester.entity.TestsResult;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PDFGenerator {

    final static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

    public static ByteArrayOutputStream generateToOutputStream(String subject, String group, List<TestsResult> testsResults){
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);

        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.addTitle("report");

            //addTitlePage(document);

            Paragraph preface = new Paragraph();
            // We add one empty line
            preface.add(new Paragraph(" "));

            // Lets write a big header
            Paragraph title = new Paragraph("                               Examination report",catFont);
            title.setAlignment(Element.ALIGN_CENTER);
            preface.add(title);

            preface.add(new Paragraph(" "));
            preface.add(new Paragraph(" "));

            Paragraph gr = new Paragraph("Group:"+group, catFont);
            gr.setAlignment(Element.ALIGN_RIGHT);
            preface.add(gr);

            preface.add(new Paragraph(" "));
            Paragraph sub = new Paragraph("Subject: "+subject, catFont);
            sub.setAlignment(Element.ALIGN_RIGHT);
            preface.add(sub);


            //two empty line
            preface.add(new Paragraph(" "));
            preface.add(new Paragraph(" "));

        /*table*/

            PdfPTable table = new PdfPTable(3);

            PdfPCell c1 = new PdfPCell(new Phrase(" # "));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Student"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Result"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            table.setHeaderRows(1);

            int i=1;
            for(TestsResult tr:testsResults){

                table.addCell("    "+i);
                table.addCell(tr.getUser().getSurname()+" "+tr.getUser().getName()+" "+tr.getUser().getMiddleName());
                table.addCell("    "+tr.getMarkPercents());
                i++;
            }
            preface.add(table);
            preface.add(new Paragraph(" "));
            preface.add(new Paragraph(" "));
            Paragraph sign = (new Paragraph("Signature of the examiner ______________         Date_________________ ", smallBold));
            sign.setAlignment(Element.ALIGN_CENTER);
            preface.add(sign);

            document.add(preface);
            document.close();
        } catch (Exception e) {
            logger.error("Error by sending outputStream in method generateToOutputStream()");
            e.printStackTrace();
        }
        return outputStream;
    }


}
