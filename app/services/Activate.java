package services;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.SqlRow;
import models.ebeanModel.Doctors;
import java.util.UUID;

public class Activate {

	public static void activatetoken( String clientmail, String token1){

	 	Doctors obj= new Doctors(); 
	 	obj.email=clientmail;
	 	obj.token=token1;
	 	obj.activate=false;
	 	obj.save();
    }  
}