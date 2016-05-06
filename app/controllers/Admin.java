package controllers;

import play.Play;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import play.mvc.Http.Context;

import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import models.ebeanModel.Doctors;
import models.ebeanModel.Practices;

import services.Activate;
import play.libs.Json;
import services.User;
import java.util.List;
import org.apache.commons.mail.*; 

import play.data.Form;
import play.data.DynamicForm;


import java.util.UUID;


import com.fasterxml.jackson.databind.ObjectMapper;

public class Admin extends Controller {

    public Result admin() {
    	
		return Results.ok(views.html.mail.render());
    }


    public Result sendmail() {

        DynamicForm bindedForm = Form.form().bindFromRequest();
        System.out.println(bindedForm);
        String clientmail=bindedForm.get("email");
        System.out.println(clientmail);
        String smtpHost = Play.application().configuration().getString("mail.smtp.host");
        Integer smtpPort = Play.application().configuration().getInt("mail.smtp.port");
        String smtpUser = Play.application().configuration().getString("mail.smtp.user");
        String smtpPassword = Play.application().configuration().getString("mail.smtp.pass");
        String sessionToken = UUID.randomUUID().toString();

        SimpleEmail mail = new SimpleEmail();
        try {
            mail.setFrom(smtpUser);
            mail.setSubject("varification");
            mail.setMsg("http://localhost:9000/admin/"+sessionToken+"/activate");
            mail.addTo(clientmail);
        } catch (EmailException e) {
            e.printStackTrace();
        }
        mail.setHostName( smtpHost );
        if ( smtpPort != null && smtpPort > 1 && smtpPort < 65536 ) {
            mail.setSmtpPort( smtpPort );
            // mail.setSSL(true);
        }
        if ( ! smtpUser.isEmpty() ) {
            mail.setAuthentication( smtpUser, smtpPassword );
            mail.setTLS(true);
        }

        try {
            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
        Activate.activatetoken(clientmail,sessionToken);

        return redirect(controllers.routes.Admin.admin());
    }

    public Result setPassword(String token){
            
        String query1="SELECT * from doctors where token='"+token+"'AND activate=0";
        SqlQuery sqlQuery1 = Ebean.createSqlQuery(query1);
        List<SqlRow>list=sqlQuery1.findList();
        if(list.isEmpty())
            return Results.ok("Token has been Activated");
        return Results.ok(views.html.activate.render(token));
    }

    public Result savePassword( String token) throws 
            java.security.NoSuchAlgorithmException,java.security.spec.InvalidKeySpecException {
               
        DynamicForm requestData = Form.form().bindFromRequest();
        String securepassword=utils.PasswordUtils.generateSecurePassword(requestData.get("password"));
        String query="UPDATE doctors SET encryptedpassword = '"+securepassword+"',  name= '"+requestData.get("name")+"', activate = 1 WHERE token = '"+token+"' ";
        SqlUpdate sqlQuery = Ebean.createSqlUpdate(query);
        Ebean.execute(sqlQuery);
        return redirect(controllers.routes.Login.login());
    }  
}