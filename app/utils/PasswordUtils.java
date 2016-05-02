package utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import play.Play;
import play.data.validation.ValidationError;

public class PasswordUtils {
	
	private final static Pattern hasUppercase = Pattern.compile("[A-Z]");
	private final static Pattern hasLowercase = Pattern.compile("[a-z]");
	private final static Pattern hasNumber = Pattern.compile("\\d");
	
	/**
	 * Default Password length to generate random password
	 */
	static int passwordLength=8;
	
	
	/**
	 * Overloaded method to generate encrypted password when password is provided 
	 * otherwise generate random password and then encrypt them
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	
	public static String generateSecurePassword()
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		//return utils.SecurePassword.securePasswordGenerator(generateRandomPassword());
		return utils.SecurePassword.securePasswordGenerator(generateRandomPassword());
		
	}
	
	public static String generateSecurePassword(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return utils.SecurePassword.securePasswordGenerator(password);
	}

	public static boolean validatePassword(String suppliedPassword,
			String storedPassword) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		// split the stored password into 3 parts
		String[] parts = storedPassword.split(":");
		int iterations = Integer.parseInt(parts[0]);
		byte[] salt = fromHex(parts[1]);
		byte[] storedHash = fromHex(parts[2]);

		// Hash the supplied password using stored password's salt, no. of
		// iterations and hash length
		PBEKeySpec spec = new PBEKeySpec(suppliedPassword.toCharArray(), salt,
				iterations, 512);
		SecretKeyFactory skf = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		byte[] generatedHash = skf.generateSecret(spec).getEncoded();

		return Arrays.equals(storedHash, generatedHash);
	}

	private static byte[] fromHex(String array) {
		return DatatypeConverter.parseHexBinary(array);
	}
	
	public static String generateRandomPassword() {
		final String ALPHA_NUM = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuffer sb = new StringBuffer(passwordLength);
		for (int i = 0; i < passwordLength; i++) {
			int ndx = (int) (Math.random() * ALPHA_NUM.length());
			sb.append(ALPHA_NUM.charAt(ndx));
		}
		return sb.toString();
	}
	
	//TODO: Make this implementation same as that in User model
	public static boolean isValidPassword(String password){
		
		
		if(password.length()<8){
			return false;
		}
		
		if(!hasUppercase.matcher(password).find()){
			return false;
		}
		
		if(!hasLowercase.matcher(password).find()){
			return false;
		}
		
		if(!hasNumber.matcher(password).find()){
			return false;
		}
		
		return true;
	}

}
