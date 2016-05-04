package controllers;

import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import models.ebeanModel.Patients;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

import java.util.List;
import play.libs.Json;


public class Dashboard extends Controller {

    public Result search() {

		return Results.ok(views.html.search.render());
    }

    public Result searchPatient(String searchedText) {

    	String query = "SELECT id,name FROM patients where name like '%"+searchedText+"%' ";
        SqlQuery update = Ebean.createSqlQuery(query);
        List<SqlRow> list = update.findList();
        return Results.ok(Json.toJson(list));
    }
}