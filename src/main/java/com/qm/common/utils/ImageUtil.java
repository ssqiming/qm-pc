package com.qm.common.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @ClassName: ImageUtil
 * @Description: 图片工具类
 * @author qiming
 * @date : 2015年11月13日 上午10:23:37
 */
public class ImageUtil {
	/**
	 * @Title: writeImg 
	 * @Description: 根据参数创建图片流
	 * @author caizhen   
	 * @param @param file   文件流
	 * @param @param tail   文件类型(如.jpg、.jnp等)
	 * @param @param path   存放路径
	 * @param @param width  文件长
	 * @param @param height 文件高
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 */
	public static void writeImg(MultipartFile file,String soucePath,String tail,String path, int width ,int height) throws Exception{
		//对图片进行转换
		InputStream fis = new FileInputStream(soucePath);//file.getInputStream();
		FileOutputStream fos = new FileOutputStream(path);
		ImageUtil.convertFormat(fis, fos, tail, tail.substring(1, tail.length()), width, height);
		fos.flush();
		fos.close();
		fis.close();
	}
	public static boolean convertFormat(InputStream infile, OutputStream outfile, String srcFormat, String destFormat, int width, int height) throws Exception {
		boolean flag = false;
		BufferedImage src = ImageIO.read(infile);
		if(height > 0  && width > 0) {
//            height = src.getHeight() > height ? height: src.getHeight();
//            width = src.getWidth() > width ? width : src.getWidth();
			//等比例缩放，且按照最大缩放比例缩放
			int twidth = src.getWidth(); 
			int theight = src.getHeight();
			if(twidth > width && theight > height) {
				if(theight*width/twidth > height) {
					width = twidth*height/theight;
				} else {
					height = theight*width/twidth;
				}
			} else if(twidth > width && theight < height) {
				height = theight*width/twidth;
			} else if (twidth < width && theight > height) {
				width = twidth*height/theight;
			} else {
				width = twidth;
				height = theight;
			}
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);//这个是用来进行图片大小调整的     
            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);     
            Graphics g = tag.getGraphics();
            //可在下面对图片进行绘制和更改
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图     
            g.dispose();
            tag.flush();
            flag = ImageIO.write(tag, destFormat, outfile);// 输出经过缩放的图片
        } else {
            flag = ImageIO.write(src, destFormat, outfile);//输出原分辨率的图片
        }
		return flag;
	}
}
