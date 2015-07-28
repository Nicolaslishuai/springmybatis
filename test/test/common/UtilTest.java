package test.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import lishuai.common.util.IdentifyingCodeUtil;

import org.junit.Test;

public class UtilTest {
	
	@Test
	public void test(){
		IdentifyingCodeUtil util=new IdentifyingCodeUtil();
		BufferedImage image=util.createIdentifyingCode(IdentifyingCodeUtil.randomCode(5));
		File img=new File("D:/1.JPEG");
		try {
			ImageIO.write(image, "JPEG", img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
