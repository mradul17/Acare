package controllers;

import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import models.ebeanModel.Practices;
import play.mvc.Http.Context;
import services.User;
import java.util.List;

public class Practice extends Controller {

    public Result info() {


    	List<Practices> list = User.getPracticeByDoctorId(ctx().session().get("id"));
		return Results.ok(views.html.practiceprofile.render(list));
    }
}