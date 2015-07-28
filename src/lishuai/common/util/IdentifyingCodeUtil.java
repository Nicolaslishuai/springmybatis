package lishuai.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码工具类
 * @author li
 *
 */
public class IdentifyingCodeUtil {
	
	private int imgWidth=400;
	
	private int imgHeight=200;
	
	private Color bankGroundcolor=Color.WHITE;//背景色
	
	private Color boderColor=Color.GREEN;//边框颜色
	
	private String fontStyle="宋体";//字体颜色
	
	private int fontHeight=200;//字体高度
	
	private int _x=80;//X偏移量
	
	private int _y=180;//y偏移量
	
	public BufferedImage createIdentifyingCode(String code){
		
		  BufferedImage image = new BufferedImage(imgWidth, imgHeight,BufferedImage.TYPE_INT_RGB);
		  
		  Graphics2D g = image.createGraphics();
		  //设置背景色
		  g.setColor(this.bankGroundcolor);
	      g.fillRect(0, 0, this.imgWidth, this.imgHeight);
	      // 设定字体
	      g.setFont(new Font(this.fontStyle, Font.PLAIN + Font.ITALIC, this.fontHeight));
	      // 画边框
	      g.setColor(boderColor);
	      g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);
	      
	      Random random=new Random(47);
	      // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
	        g.setColor(getRandColor(160, 200));
	        for (int i = 0; i < 160; i++) {
	            int x = random.nextInt(imgWidth);
	            int y = random.nextInt(imgHeight);
	            int xl = random.nextInt(12);
	            int yl = random.nextInt(12);
	            g.drawLine(x, y, x + xl, y + yl);
	        }
        String[] codeList=code.split("");
        int i=-1;
        for(String c:codeList){
        	 g.setColor(getRandColor());
        	 g.drawString(String.valueOf(c),i*_x, _y);
        	 i++;
        }
		return image;
	}
	
	private static Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
		fc = 255;
		if (bc > 255)
		bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	/**
	 * 随机颜色
	 * @return
	 */
	private static Color getRandColor() {//给定范围获得随机颜色
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return new Color(r, g, b);
	}
	/**
	 * 随机字符
	 * @param length
	 * @return
	 */
	public static String randomCode(int length){
		char[] charlist=new char[length];
		for(int i=0;i<charlist.length;i++){
			int deviation=Math.random()>0.5?65:97;
			charlist[i]=(char)(int)(Math.random()*26+deviation);
		}
		return String.valueOf(charlist);
	}
	
}
