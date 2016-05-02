package utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

//Utility to generate a PBKDF2 encrypted version of the password
//Can be used by this app as well as an external library to our command line tool which hashes a password given as an input
public class SecurePassword {

	public static String securePasswordGenerator(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		int iterations = 128000;
		char[] passwordChars = password.toCharArray();
		byte[] salt = getSalt().getBytes();
		// byte size is desirable hash size * 8, in our case 64 * 8
		int byteSize = 512;
		PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, iterations,
				byteSize);
		SecretKeyFactory skf = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);

	}

	private static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return new String(salt);
	}

	private static String toHex(byte[] array) {
		return DatatypeConverter.printHexBinary(array);

	}
}
