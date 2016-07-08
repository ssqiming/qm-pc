package com.qm.util;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.qm.web.utils.DateUtil;

public class HeaderFoote extends PdfPageEventHelper {
//	public static final String CHARACTOR_FONT_CH_FILE = "c:\\windows\\fonts\\MSYH.TTF"; // 仿宋常规
	protected BaseFont helv;
	protected BaseFont helv1;
	protected PdfTemplate total;
	protected PdfTemplate tot;
	protected PdfTemplate totRI;
	protected PdfTemplate totCBD;
	protected PdfTemplate totCBR;
	private String code="";

	public HeaderFoote(String code) {
		this.code=code;
	}

	public void onEndPage(PdfWriter writer, Document document) {
		
		PdfContentByte cb = writer.getDirectContent();
		PdfContentByte cbr = writer.getDirectContent();
		PdfContentByte cbb = writer.getDirectContent();
		cb.saveState();
		cbr.saveState();
		cbb.saveState();
		
		String text = "" + writer.getPageNumber();
		String tt = "测试文件";
		String date=new DateUtil().getCurrentDate();
		date=date.replaceAll("-", "");
		//String cbrText = "编号:ZXY"+date;
		String cbrText = "编号: "+code;
		
		float textBase = document.bottom() - 20;
		try {
			helv = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   
		            BaseFont.NOT_EMBEDDED);  
			helv1 = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   
		            BaseFont.NOT_EMBEDDED);  
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Image img;
			Image img2;
			Image img3;
			String path=HeaderFoote.class.getProtectionDomain().getCodeSource().getLocation().getFile();
			img = Image.getInstance(path.replace("WEB-INF/lib/zx-business-0.0.1-SNAPSHOT.jar", "image/")+"zhengxy.png");
			img2 = Image.getInstance(path.replace("WEB-INF/lib/zx-business-0.0.1-SNAPSHOT.jar", "image/")+"zhengxy4.png");
			img3 = Image.getInstance(path.replace("WEB-INF/lib/zx-business-0.0.1-SNAPSHOT.jar", "image/")+"zhengxy5.png");

			img.setAbsolutePosition(10, 807);
			img2.setAbsolutePosition(10, 795);
			img3.setAbsolutePosition(430, 7);

			cb.addImage(img);
			cb.addImage(img2);
//			cbdr.beginText();
//			cbdr.addImage(img3);
//			cbdr.endText();
//			cbdr.restoreState();

		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		float textSize = helv.getWidthPoint(tt, 12);
		cb.beginText();
		cb.setFontAndSize(helv, 12);

		cb.setTextMatrix(250, 810);
		cb.showText(tt);
		cb.endText();
		tot = writer.getDirectContent().createTemplate(100, 400);
		cb.addTemplate(tot, document.top() + textSize, 300);
		cb.restoreState();

		cbr.beginText();
		cbr.setFontAndSize(helv, 12);
		cbr.setTextMatrix(465, 810);
		cbr.showText(cbrText);
		cbr.endText();
		totRI = writer.getDirectContent().createTemplate(100, 400);
		cbr.addTemplate(totRI, document.top() + textSize, 300);
		cbr.restoreState();

		cbb.beginText();
		cbb.setFontAndSize(helv, 12);

		cbb.setTextMatrix(document.left(), textBase);
		cbb.showText(text);
		cbb.endText();
		total = writer.getDirectContent().createTemplate(100, 100);
		cbb.addTemplate(total, document.left() + textSize, textBase);
		cbb.restoreState();
		
//		cbd.beginText();
//		cbd.setFontAndSize(helv1, 8);
//		cbd.setTextMatrix(290, 30);
//		cbd.showText(cbdText);
//		cbd.endText();
		totCBD = writer.getDirectContent().createTemplate(100, 400);
//		cbd.addTemplate(totCBD, document.top() + textSize, 300);
//		cbd.restoreState();

		
	}

	public void onEndPage1(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("art");

		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_RIGHT, new Phrase("even header"),
				rect.getRight(), rect.getTop(), 0);

		ColumnText.showTextAligned(writer.getDirectContent(),
				Element.ALIGN_CENTER,
				new Phrase(String.format("page %d", writer.getPageNumber())),
				(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18,
				0);
	}
}