package services;

import java.util.UUID;
import models.ebeanModel.LoginSession;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Ebean;

public class UserSession {

	public static Boolean saveSessionToken(String userId, String token,String ip) {

		String query = "INSERT INTO login_session (user_id, token, create_at, expire_at, expired, ip_addresses) VALUES"+
				" ('"+userId+"', '"+token+"', NOW(), DATE_ADD(NOW(), INTERVAL 180 SECOND), 0, '"+ip+"')";
		SqlUpdate update = Ebean.createSqlUpdate(query);

		if(Ebean.execute(update)>0){
			return true;
		}
		return false;
	}

	public static Boolean extendSession(String token) {

		String query = "update login_session set expire_at = DATE_ADD(NOW(), INTERVAL 180 SECOND) where token = "+token+" and expired = 0 ";
		SqlUpdate update = Ebean.createSqlUpdate(query);

		if(Ebean.execute(update)>0){
			return true;
		}
		return false;
	}

	public static boolean invalidateSessionToken(String userId, String token) {

		String query = "UPDATE login_session SET expired = 1 WHERE token = '"+token+"' AND userId = '"+userId+"' ";
		SqlUpdate update = Ebean.createSqlUpdate(query);

		if(Ebean.execute(update)>0){
			return true;
		}
		return false;
	}
}