package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageUtils {
	
	public static Boolean doesFilePathExist(String path) {	
		return new File(path).exists();
	}
	
	public static byte[] loadImage(String path, String extension) throws IOException{
		File file = new File(path);
		BufferedImage image = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, extension, baos);
		return baos.toByteArray();
	}
}
