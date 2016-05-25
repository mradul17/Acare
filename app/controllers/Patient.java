 package controllers;

import play.Play;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

import java.util.List;

import play.data.Form;
import play.data.DynamicForm;

import models.ebeanModel.Patients;

import play.libs.Json;
import com.avaje.ebean.text.json.EJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

public class Patient extends Controller {

    public Result add() {

		return Results.ok(views.html.patient.render(null));
    }

    public Result list() {

    	String query = "SELECT patients.id,patients.name,patients.dob,patients.email,"+
                "patients.contactnumber1,careplan_in_json.id as cid FROM patients left join careplan_in_json"+
                " on patients.id=careplan_in_json.patientid";
        SqlQuery sqlQuery = Ebean.createSqlQuery(query);
        List<SqlRow> list = sqlQuery.findList();
		return Results.ok(views.html.patientlist.render(list));
    }

    public Result searchList(String str) {

        String query = "SELECT id,name,dob,email,contactNumber1 FROM patients where name like '%"+str+"%' ";
        SqlQuery sqlQuery = Ebean.createSqlQuery(query);
        List<SqlRow> list = sqlQuery.findList();
        return Results.ok(views.html.patientlist.render(list));
    }

    public Result save() throws java.io.IOException {

		DynamicForm requestData = Form.form().bindFromRequest();
		String formData = Json.toJson(requestData.data()).toString();
		ObjectMapper mapper = new ObjectMapper();
		Patients patient = mapper.readValue(formData, Patients.class);
		patient.save();

		List<Patients> list = Ebean.find(Patients.class)
			.select("id")
          	.where()
          	.eq("email", requestData.get("email"))
          	.findList();

        //String patientId = list.get(0).id.toString();
        String ipath = Play.application().configuration().getString("userFilePath")+ "/" + "patients/" + list.get(0).id.toString();
		MultipartFormData <File> body = request().body().asMultipartFormData();
		FilePart <File> photo = body.getFile("photo");
		if (photo != null) {
            String fileName = photo.getFilename();
            File file = photo.getFile();
            File newFile = new File(ipath);
            file.renameTo(newFile);        
        }

        flash("success", "Patient detail saved");
		return redirect(controllers.routes.Patient.add());
    }

    public Result detail(Long id) {

        List<Patients> list = Ebean.find(Patients.class)
            .select("id")
            .where()
            .eq("id", id)
            .findList();
        return Results.ok(views.html.patient.render(list));
    }

    public Result update(Long id) {

        DynamicForm requestData = Form.form().bindFromRequest();
        Patients patient = Ebean.find(Patients.class, id);
        patient.name = requestData.get("name");
        patient.dob = requestData.get("dob");
        patient.address1 = requestData.get("address1");
        patient.address2 = requestData.get("address2");
        patient.pinCode = requestData.get("pinCode");
        patient.state = requestData.get("state");
        patient.country = requestData.get("country");
        patient.contactNumber1 = requestData.get("contactNumber1");
        patient.contactNumber2 = requestData.get("contactNumber2");
        patient.email = requestData.get("email");
        Ebean.save(patient);
        
        MultipartFormData <File> body = request().body().asMultipartFormData();
        String ipath = Play.application().configuration().getString("userFilePath")+ "/" + "patients/" + id.toString();
        FilePart <File> photo = body.getFile("photo");
        if (photo != null) {
            String fileName = photo.getFilename();
            File file = photo.getFile();

            File newFile = new File(ipath);
            file.renameTo(newFile);        
        }

        return redirect(controllers.routes.Patient.detail(id));
    }
}