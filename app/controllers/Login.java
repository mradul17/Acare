package controllers;

import services.User;
import services.UserSession;

import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import play.mvc.Http.Context;

import play.data.Form;

import models.forms.LoginForm;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import models.ebeanModel.Doctors;
import com.avaje.ebean.SqlRow;
import play.mvc.BodyParser;
import play.libs.Json;


public class Login extends Controller{

	public Result login() {
       // return Results.ok(views.html.careplan.render());
		return Results.ok(views.html.login.render(null));
    }

    public Result authenticate() {

    	session().clear();
    	Form<LoginForm> loginForm = Form.form(LoginForm.class);
    	Form<LoginForm> bindedLoginForm = loginForm.bindFromRequest();

    	if(bindedLoginForm.hasErrors()) {
    		return Results.badRequest(views.html.login.render(bindedLoginForm));
    	}

    	List<Doctors> list = User.getUserByEmail(bindedLoginForm.get().email);

        String id = list.get(0).id.toString();
        String sessionToken = UUID.randomUUID().toString();
        if(UserSession.saveSessionToken(id, sessionToken, request().remoteAddress())){

        }

    	session("id",id);
    	session("name",list.get(0).name);
        response().setCookie("token",sessionToken);

    	return redirect(controllers.routes.Dashboard.search());
    }

    public Result extendToken(String token) {
        
        System.out.println("--"+token);
        if (UserSession.extendSession(token)) {
            return ok("Token's lifetime extended");
        } else {        
            return forbidden("Token could not be extended");
        }
    }

    public Result inactivityLogout() {

        System.out.println("++"+Context.current().request().cookie("sessionToken").toString());
        UserSession.invalidateSessionToken(ctx().session().get("id"),
                Context.current().request().cookie("sessionToken").toString()); 
        session().clear();
        response().discardCookie("sessionToken");
        return Results.ok(views.html.login.render(null));
    }
}