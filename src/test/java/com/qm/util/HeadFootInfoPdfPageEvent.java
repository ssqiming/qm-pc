package com.qm.util;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.qm.web.utils.DateUtil;

public    class HeadFootInfoPdfPageEvent extends PdfPageEventHelper {
    //自定义传参数
    public String pdfName;//出入库名称
    public String date;//日期
    public String type;//单号类型
    public String code;//单号
    public PdfTemplate tpl;
    BaseFont bfChinese;
    BaseFont cChinese;

    //无参构造方法
    public HeadFootInfoPdfPageEvent() {
        super();
    }

    //有参构造方法
    public HeadFootInfoPdfPageEvent(String PdfName,String Date,String Type,String Code) {
        super();
        this.pdfName=PdfName;
        this.date = Date;
        this.type=Type;
        this.code = Code;
        try {
            bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            cChinese=BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.HELVETICA_BOLD, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        tpl = writer.getDirectContent().createTemplate(100, 20);
    }

    //实现页眉和页脚的方法
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            String[] riqi = date.split("-");
            PdfContentByte headAndFootPdfContent = writer.getDirectContent();
            PdfContentByte headAndFootPdf = writer.getDirectContent();
            headAndFootPdfContent.saveState();
            headAndFootPdfContent.beginText();
            headAndFootPdf.saveState();
            headAndFootPdf.beginText();
            //设置中文
            headAndFootPdfContent.setFontAndSize(bfChinese, 12);
            //设置中文
            headAndFootPdfContent.setFontAndSize(bfChinese, 16);
            //文档页头信息设置  
            float x = document.top(-20);
            float x1 = document.top(-5);
            //页头信息中间  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT, pdfName, 10, 810, 0);
            //页头信息左面  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT, riqi[0] + "年" + riqi[1] + "月" + riqi[2] + "日",
//                    document.left() + 100, x1, 0);
            //页头信息中间  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER, type+"库单号：" +code+ "",
                    (document.right() + document.left()) / 2, x1, 0);
            //页头信息右面  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, "文件编号:ZXY"+new DateUtil().getCurrentDate().replaceAll("-", "") , 600, 810, 0);
            //文档页脚信息设置  
            float y = document.bottom(-20);
            float y1 = document.bottom(-35);
            //页脚信息左面  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT, "储运部负责人：", document.left() + 100, y, 0);
            //页脚信息中间  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER, "   库管员：    ", (document.right() + document.left()) / 2, y, 0);
            //页脚信息右面  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, " 经手人：", document.right() - 100, y, 0);
            //添加页码
            //页脚信息中间  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER, "--第" + document.getPageNumber(),
                    (document.right() + document.left()) / 2, y1, 0);
            //在每页结束的时候把“第x页”信息写道模版指定位置  
            headAndFootPdfContent.addTemplate(tpl, (document.right() + document.left()) / 2 + 15, y1);//定位“y页” 在具体的页面调试时候需要更改这xy的坐标  
            headAndFootPdfContent.endText();
            headAndFootPdfContent.restoreState();
        } catch (Exception de) {
            de.printStackTrace();
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        //关闭document的时候获取总页数，并把总页数按模版写道之前预留的位置  
        tpl.beginText();
        tpl.setFontAndSize(bfChinese, 12);
        tpl.showText("页,共" + Integer.toString(writer.getPageNumber() - 1) + "页--");
        tpl.endText();
        tpl.closePath();//sanityCheck();  
    }
}