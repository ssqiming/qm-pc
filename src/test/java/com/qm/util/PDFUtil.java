package com.qm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFUtil {

	public static final Rectangle PAGE_SIZE = PageSize.A4;
	public static final float MARGIN_LEFT = 50;
	public static final float MARGIN_RIGHT = 50;
	public static final float MARGIN_TOP = 50;
	public static final float MARGIN_BOTTOM = 50;
	public static final float SPACING = 20;
	
	private Document document = null;
	private Chapter chapter=null;
	
	/**
	 * @Description: 创建文档
	* @author: qiming
	* @date: 2016年7月8日 下午3:31:54
	 */
	private void createDocument(String filename, String code) {
		File file = new File(filename);
		FileOutputStream out = null;
		document = new Document(PAGE_SIZE, MARGIN_LEFT, MARGIN_RIGHT,
				MARGIN_TOP, MARGIN_BOTTOM);
		try {
			out = new FileOutputStream(file);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			Rectangle rect = new Rectangle(36, 54, 559, 788);
			rect.setBorderColor(BaseColor.BLACK);
			writer.setBoxSize("art", rect);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.open();
	}
	
	/**
	 * @Description: 返回中文字体--仿宋
	* @author: qiming
	* @date: 2016年7月8日 下午5:01:49
	 */
	public static Font createCHineseFont(float size, int style, BaseColor color) {
		BaseFont bfChinese = null;
		try {						
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Font(bfChinese, size, style, color);
	}
	
	/**
	 * @Description: 创建PDF文档中的章节
	* @author: qiming
	* @date: 2016年7月8日 下午5:20:56
	* 
	*  @param title  章节标题
	* @param chapterNum  章节序列号
	* @param alignment  0表示align=left，1表示align=center
	* @param numberDepth  章节是否带序号 设值=1 表示带序号 1.章节一；1.1小节一...，设值=0表示不带序号
	* @param font  字体格式
	* @return Chapter章节
	*/
	public static Chapter createChapter(String title, int chapterNum,
			int alignment, int numberDepth, Font font) {
		Paragraph chapterTitle = new Paragraph(title, font);
		chapterTitle.setSpacingAfter(20);
		chapterTitle.setAlignment(alignment);
		Chapter chapter = new Chapter(chapterTitle, chapterNum);
		chapter.setNumberDepth(numberDepth);
		return chapter;
	}
	
	/**
	 * @Description: 创建指定章节下的小节
	* @author: qiming
	* @date: 2016年7月8日 下午5:23:37
	* 
	* @param chapter  指定章节
	* @param title  小节标题
	* @param font  字体格式
	* @param numberDepth  小节是否带序号 设值=1 表示带序号 1.章节一；1.1小节一...，设值=0表示不带序号
	* @return section在指定章节后追加小节
	*/
	public static Section createSection(Chapter chapter, String title, Font font, int numberDepth) {
		Section section = null;
		if (chapter != null) {
			Paragraph sectionTitle = new Paragraph(title, font);
			sectionTitle.setSpacingBefore(SPACING);
			sectionTitle.setSpacingBefore(20);
			sectionTitle.setSpacingAfter(30);
			section = chapter.addSection(sectionTitle);
			section.setNumberDepth(numberDepth);
		}
		return section;
	}
	
	/**
	 * @Description: 创建文档起始页
	* @author: qiming
	* @date: 2016年7月8日 下午5:08:50
	 */
	private void createStartPage(String str) {
		PdfPTable table1 = new PdfPTable(1);
		int hws1[] = { 200 };
		PdfPCell newcell = null;
		try {
			table1.setWidths(hws1);
			table1.setTotalWidth(300);
			Font titleFont = PDFUtil.createCHineseFont(16, Font.UNDEFINED, new BaseColor(0, 0, 0));
			newcell = new PdfPCell();
			newcell.setBorder(0);
			Paragraph pa = new Paragraph(str, titleFont);
			pa.setAlignment(0);
			newcell.setMinimumHeight(230);
			newcell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			newcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			newcell.setPhrase(pa);
			table1.addCell(newcell);
			Chapter chapter = PDFUtil.createChapter("测试文档", 1, 1, 0, titleFont);
			writeChapterToDoc(chapter);
//			document.add(table1);
		} catch (DocumentException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * @Description: 报告结束页
	* @author: qiming
	* @date: 2016年7月11日 上午9:28:49
	 */
	private void createEndPage() {
		Font chapterFont = PDFUtil.createCHineseFont(15, Font.UNDEFINED, new BaseColor(0, 0, 0));
		Font textFont = PDFUtil.createCHineseFont(9, Font.UNDEFINED, new BaseColor(0, 0, 0));
		Chapter chapter = PDFUtil.createChapter("报告结束", 1, 1, 0, chapterFont);
		Section section1 = PDFUtil.createSection(chapter, "免责申明", textFont, 0);
		Phrase text1 = new Phrase("这是测试文档，文档创建结束。",textFont);
		Paragraph paragraph = new Paragraph();
		paragraph.add(text1);
		paragraph.setFirstLineIndent(20);
		section1.add(paragraph);
		writeChapterToDoc(chapter);
		
		Image img;
		try {
			img = Image.getInstance("E:\\pdftest\\tryGit.jpg");
			img.setAbsolutePosition(80, 300);
			document.add(img);
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
	}
	
	/**
	 * @Description: 将章节写入文档中
	* @author: qiming
	* @date: 2016年7月11日 上午9:25:35
	 */
	public void writeChapterToDoc(Chapter chapter) {
		try {
			if (document != null) {
				if (!document.isOpen())
					document.open();
				document.add(chapter);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 关闭文档
	* @author: qiming
	* @date: 2016年7月11日 上午9:27:07
	 */
	public void closeDocument() {
		if (document != null) {
			document.close();
		}
	}
	
	public static void main(String[] args) {
		PDFUtil pdfUtil = new PDFUtil();
		pdfUtil.createDocument("E:\\pdftest\\测试.pdf", "20160708");
		pdfUtil.createStartPage("测试文档");
		pdfUtil.createEndPage();
		pdfUtil.closeDocument();
		System.out.println("OVER================================");
	}

}
