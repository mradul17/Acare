package utils;

import java.io.File;

import play.Play;

public class ImageUtils {
	
	public static Boolean doesDfilePathExist(Long path) {	

		String ipath = Play.application().configuration().getString("userFilePath")+ "/" + "doctors/" + path.toString(); 
		return new File(ipath).exists();
	}

	public static Boolean doesPfilePathExist(Long path) {	

		String ipath = Play.application().configuration().getString("userFilePath")+ "/" + "patients/" + path.toString(); 
		return new File(ipath).exists();
	}
}
