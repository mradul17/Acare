package controllers;

import services.User;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;

import play.data.Form;

import models.forms.LoginForm;

import java.util.List;
import models.ebeanModel.Doctors;
import com.avaje.ebean.SqlRow;

public class Login extends Controller{

	public Result login() {
		
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
    	session("id",list.get(0).id.toString());
    	session("name",list.get(0).name);

    	return redirect(controllers.routes.Dashboard.search());
    }
}