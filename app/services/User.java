package services;

import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import utils.PasswordUtils;
import models.ebeanModel.Practices;
import models.ebeanModel.Doctors;
import models.ebeanModel.PracticesDoctors;
import play.libs.Json;
public class User {

	public static Boolean isValid(String email,String password) {

		List<Doctors> list = getUserByEmail(email);
		if (list.isEmpty()) {
			return false;
		}
		try {
			return PasswordUtils.validatePassword(password,
				list.get(0).encryptedPassword);
		}
		catch(NoSuchAlgorithmException ne) {
		}
		catch(InvalidKeySpecException ie) {
		}
		return false; 
	}

	public static List<Doctors> getUserByEmail(String email) {

		List<Doctors> list = Doctors.find.where().eq("email", email).findList();
		return list;
	}

	public static List<Doctors> getUserById(String id) {

		List<Doctors> list = Doctors.find.where().eq("id", id).findList();
		return list;
	}

	public static List<Practices> getPracticeByDoctorId(String id) {

		//TODO improve Ebean query
		List<Practices> list = null;
		List<PracticesDoctors> pd = PracticesDoctors.find.select("pid").where().eq("id", id).findList(); 
		if(pd.isEmpty()==false){
			list = Practices.find.where().eq("id", pd.get(0).pid.id).findList();
		}
		return list;
	}
}