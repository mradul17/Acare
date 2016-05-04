package controllers;

import services.User;
import services.UserSession;

import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;

import play.data.Form;

import models.forms.LoginForm;

import java.util.List;
import java.util.UUID;

import models.ebeanModel.Doctors;
import com.avaje.ebean.SqlRow;
import play.mvc.BodyParser;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

public class Login extends Controller{

	public Result login() {
       // return Results.ok(views.html.careplan.render());
		return Results.ok(views.html.login.render(null));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result rec() {

        JsonNode json = request().body().asJson();
        System.out.println(json);
        return ok(json);
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
        UserSession.saveSessionToken(id, sessionToken, request().remoteAddress());

    	session("id",id);
    	session("name",list.get(0).name);
        response().setCookie("token",sessionToken);

    	return redirect(controllers.routes.Dashboard.search());
    }

    public static Result extendToken(String token) {
        
    if (UserSession.extendSession(token)) {
        return ok("Token's lifetime extended");
    } else {        
        return forbidden("Token could not be extended");
    }
    }
}