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
		int modifiedCount = Ebean.execute(update);
		return false;
	}

	public static Boolean extendSession(String token) {

		String query = "update table login_session set expire_at = NOW() where token = "+token+" and expired = 0 ";
		SqlUpdate update = Ebean.createSqlUpdate(query);
		int modifiedCount = Ebean.execute(update);
		return false;
	}
}