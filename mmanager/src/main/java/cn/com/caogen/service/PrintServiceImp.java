package cn.com.caogen.service;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import cn.com.caogen.controller.UserController;
import cn.com.caogen.util.DateUtil;
import com.lowagie.text.DocumentException;
//import com.nudms.server.nurse.servlet.CompressDataServlet;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.util.Calendar;
import java.util.Map;

/**
 * 打印表单
 * @author
 *
 */
public class PrintServiceImp {
    private static Logger logger = LoggerFactory.getLogger(PrintServiceImp.class);
    public static String printmenu(String filepath, String printName, Map<String,Object> map,String type,String moneynum) throws  IOException,DocumentException, PrinterException{
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String day = String.valueOf(date.get(Calendar.DATE));
        String month = String.valueOf(date.get(Calendar.MONTH)+1);
        String countid=map.get("username").toString();
//        String srccounttype=map.get("srccounttype").toString();
//        String srcnum=map.get("srcnum").toString();
//        String destcounttype=map.get("destcounttype").toString();//手持类型
//        String destnum=map.get("destnum").toString();//手持金额
        String servicebranch=map.get("servicebranch").toString();//网点
        String thisrate=map.get("thisrate").toString();
        String  snumber=map.get("snumber").toString();
        int j=0;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if(services.length == 0){
            return "";
        }
        PrinterJob job = PrinterJob.getPrinterJob();
        for(PrintService ps: services){
            if(ps.getName().equals(printName)){
                job.setPrintService(services[j]);
            }else {
                j++;
            }
        }
        FileInputStream fis = new FileInputStream(ResourceUtils.getFile(filepath));
        byte[] pdfContent = new byte[fis.available()];
        fis.read(pdfContent, 0, fis.available());
        ByteBuffer buf = ByteBuffer.wrap(pdfContent);
        PDFFile pdfFile = new PDFFile(buf);
        Book bk = new Book();
        int num = pdfFile.getNumPages();
        for(int i=0; i<num; i++){
            PDFPage page = pdfFile.getPage(i+1);
            PageFormat pf = job.defaultPage();
            bk.append(new Printable() {
                          @Override
                          public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                              if(pageIndex>0){
                                  return NO_SUCH_PAGE;
                              }
                              int yIndex = 30;
                              Image image;
                              Graphics2D graphics2D = (Graphics2D) graphics;
                              graphics2D.setStroke(new BasicStroke(0.5f));
                              Font font = new Font("宋体", Font.CENTER_BASELINE, 10);
                              graphics2D.setFont(font);
                              Color defaultColor = graphics2D.getColor();
                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 10));
                              graphics2D.setColor(defaultColor);
                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 10));
                              yIndex = drawString(graphics2D, year, 282, 58, 250, 30);
                              yIndex = drawString(graphics2D, month, 331, 58, 250, 30);
                              yIndex = drawString(graphics2D, day, 373, 58, 250, 30);
                              //回单
                              yIndex = drawString(graphics2D, year, 476, 58, 250, 30);
                              yIndex = drawString(graphics2D, month, 520, 58, 250, 30);
                              yIndex = drawString(graphics2D, day, 560, 58, 250, 30);

                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 12));
                              yIndex = drawString(graphics2D, countid, 82, 105, 250, 30);
                              yIndex = drawString(graphics2D, type, 192, 105, 250, 30);
                              yIndex = drawString(graphics2D, thisrate, 332, 105, 250, 30);
                              yIndex = drawString(graphics2D, moneynum, 102, 185, 250, 30);

                              graphics2D.setFont(new Font("宋体",Font.CENTER_BASELINE, 10));
                              yIndex = drawString(graphics2D, countid, 505, 105, 250, 30);
                              yIndex = drawString(graphics2D, type, 502, 135, 250, 30);
                              yIndex = drawString(graphics2D, thisrate, 505, 155, 250, 30);
                              yIndex = drawString(graphics2D, moneynum, 505, 185, 250, 30);
                             // yIndex = drawString(graphics2D, "1%", 505, 210, 250, 30);
                              yIndex = drawString(graphics2D, snumber, 490, 305, 250, 30);
                              yIndex = drawString(graphics2D, snumber, 42, 335, 250, 30);


                              yIndex = drawString(graphics2D, servicebranch, 202, 378, 250, 30);
                              yIndex = drawString(graphics2D, servicebranch, 505, 378, 250, 30);

                              return PAGE_EXISTS;
                          }
                      }
                    , pf);

            Paper paper = pf.getPaper();
            if(paper==null){
                return "";
            }
            double x = 0;
            double y = 0;
            if(page.getAspectRatio()<1){
                double width = page.getBBox().getWidth();
                double height = page.getBBox().getHeight();
                paper.setImageableArea(x, y, width, height);
                pf.setOrientation(PageFormat.PORTRAIT);
            }else{

                double width = page.getBBox().getHeight();
                double height = page.getBBox().getWidth();
                paper.setImageableArea(x, y, width, height);
                pf.setOrientation(PageFormat.LANDSCAPE);
            }
            pf.setPaper(paper);
        }
        job.setPageable(bk);
        job.setJobName("My book");
        try {
            logger.info("print start");
            job.print();
            logger.info("print end");
        } catch (Exception e) {
            logger.info("print fial");
            return "";
        }
        return "success";
    }

    private static int drawString(Graphics2D graphics2D, String text, int x, int y, int lineWidth, int lineHeight){
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        if(fontMetrics.stringWidth(text)<lineWidth){
            graphics2D.drawString(text, x, y);
            return y;
        } else{
            char[] chars = text.toCharArray();
            int charsWidth = 0;
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<chars.length; i++){
                if((charsWidth + fontMetrics.charWidth(chars[i]))>lineWidth){
                    graphics2D.drawString(sb.toString(), x, y);
                    sb.setLength(0);
                    y = y + lineHeight;
                    charsWidth = fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                } else{
                    charsWidth = charsWidth + fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                }
            }
            if(sb.length()>0){
                graphics2D.drawString(sb.toString(), x, y);
                y = y + lineHeight;
            }
            return y - lineHeight;
        }
    }
//    public static void main(String  args[]) throws IOException, DocumentException, PrinterException
//    {
//        PrintServiceImp pic = new PrintServiceImp();
//       // pic.printmenu("D:\\1.pdf", "1.pdf");
//
//    }


}

