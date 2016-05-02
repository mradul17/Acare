package controllers;

import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import play.mvc.Http.Context;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

import models.ebeanModel.Doctors;
import models.ebeanModel.Practices;
import play.libs.Json;
import services.User;
import java.util.List;

import play.data.Form;
import play.data.DynamicForm;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Doctor extends Controller {

    public Result profile() {

    	List<Doctors> list = User.getUserById(ctx().session().get("id"));
    	if(list.isEmpty()) {
    		return badRequest(" No User in Current Sesssion");
    	}
		return Results.ok(views.html.doctorprofile.render(list));
    }

    public Result add() {

    	List<Practices> list = User.getPracticeByDoctorId(ctx().session().get("id"));
    	if(list.isEmpty()) {
    		return badRequest(" No User in Current Sesssion");
    	}
    	System.out.println(Json.toJson(list));
		return Results.ok(Json.toJson(list)).as("application/json");
    }

    public Result logout(){

        session().clear();
        return Results.ok(views.html.login.render(null));
    }

    public Result update(Long id){

        DynamicForm requestData = Form.form().bindFromRequest();

        Doctors doctor = Ebean.find(Doctors.class, id);
        doctor.address1 = requestData.get("address1");
        doctor.address2 = requestData.get("address2");
        doctor.pinCode = requestData.get("pinCode");
        doctor.state = requestData.get("state");
        doctor.country = requestData.get("country");
        doctor.dayTimePhonenumber = requestData.get("dayTimePhonenumber");
        doctor.offTimePhonenumber = requestData.get("offTimePhonenumber");
        doctor.email = requestData.get("email");
        Ebean.save(doctor);
        
        List<Doctors> list = User.getUserById(ctx().session().get("id"));
        return Results.ok(views.html.doctorprofile.render(list));
    }
}