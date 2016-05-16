package controllers;

import play.Play;
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

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

public class Doctor extends Controller {

    public Result profile() {

    	List<Doctors> list = User.getUserById(ctx().session().get("id"));
    	if(list.isEmpty()) {
    		return badRequest(" No User in Current Sesssion");
    	}
		return Results.ok(views.html.doctorprofile.render(list));
    }

    public Result getPracticeAddress() {

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
        
        MultipartFormData <File> body = request().body().asMultipartFormData();
        FilePart <File> photo = body.getFile("photo");
        if (photo != null) {
            String fileName = photo.getFilename();
            File file = photo.getFile();
            String ipath = Play.application().configuration().getString("userFilePath")+ "/" + "doctors/" + id; 
            File newFile = new File(ipath);
            file.renameTo(newFile);        
        }

        return redirect(controllers.routes.Doctor.profile());
    }
}