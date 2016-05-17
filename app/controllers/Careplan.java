package controllers;


import play.libs.ws.*;
import java.util.concurrent.CompletionStage;


import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import javax.inject.Inject;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

import java.util.List;

import play.data.Form;
import play.data.DynamicForm;
import models.ebeanModel.Careplans;
import play.libs.Json;

import com.avaje.ebean.text.json.EJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

public class Careplan extends Controller {

    @Inject WSClient ws;
    public Result careplan(Long id) {
		System.out.println("CarePlan");
		return Results.ok(views.html.careplan.render(id));
    }
    public Result save(Long id) throws java.io.IOException{
		System.out.println("Care save");
		JsonNode json = request().body().asJson();
		System.out.println(json);
		String formData = json.toString();
		List<Careplans> list = Ebean.find(Careplans.class)
		 .select("id")
		 .where()
		 .eq("patientid",id)
          	.findList();
		//Careplans careplan = new Careplans();
          	if(list.isEmpty())
          	{
          		Careplans careplan = new Careplans();
          		careplan.madicationName=formData;
				careplan.patientid=id;
				careplan.save();
			}
			else
			{
		Careplans careplan = Ebean.find(Careplans.class, list.get(0).id);
			careplan.madicationName=formData;
				//careplan.patientid=id;
			Ebean.save(careplan);
			
			}
		//careplan.patientid=id;
		

		String data ="{"
	+" \"Meta\": {"
	+"	\"DataModel\": \"Clinical Summary\","
	+"	\"EventType\": \"Push\","
	+"	\"EventDateTime\": \"2016-05-04T06:32:49.396Z\","
	+"	\"Test\": true,"
	+"	\"Destinations\": ["
	+"		{"
	+"			\"ID\": \"ef9e7448-7f65-4432-aa96-059647e9b358\","
	+"			\"Name\": \"Clinical Summary Push Endpoint\""
	+"		}"
	+"	]"
	+"},"
	+"\"Document\": {"
	+"	\"ID\": \"75cb4ad4-e5f9-4cd3-8750-eb5050521e0d\","
	+"	\"Author\": {"
	+"		\"ID\": 4356789876,"
	+"		\"IDType\": \"NPI\","
	+"		\"Type\": null,"
	+"		\"FirstName\": \"Pat\","
	+"		\"LastName\": \"Granite\","
	+"		\"Credentials\": ["
	+"			\"MD\""
	+"		],"
	+"		\"Address\": {"
	+"			\"StreetAddress\": \"123 Main St.\","
	+"			\"City\": \"Madison\","
	+"			\"State\": \"WI\","
	+"			\"ZIP\": \"53703\","
	+"			\"County\": \"Dane\","
	+"			\"Country\": \"USA\""
	+"		},"
	+"		\"Location\": {"
	+"			\"Type\": null,"
	+"			\"Facility\": null,"
	+"			\"Department\": null"
	+"		},"
	+"		\"PhoneNumber\": {"
	+"			\"Office\": null"
	+"		}"
	+"	},"
	+"	\"Visit\": {"
	+"		\"Location\": {"
	+"			\"Type\": \"Inpatient\","
	+"			\"Facility\": \"RES General Hospital\","
	+"			\"Department\": \"3N\""
	+"		},"
	+"		\"StartDateTime\": \"2016-05-04T10:25:52.955Z\","
	+"		\"Reason\": \"Annual Physical\","
	+"		\"EndDateTime\": \"2016-05-04T10:25:52.955Z\""
	+"	}"
	+"},"
	+"\"Patient\": {"
	+"	\"Identifiers\": ["
	+"		{"
	+"			\"ID\": \"a1d4ee8aba494ca^^^&1.3.6.1.4.1.21367.2005.13.20.1000&ISO\","
	+"			\"IDType\": \"NIST\""
	+"		}"
	+"	]"
	+"},"
	+"\"Data\": "+json+""
+"}";
System.out.println(data);
		ws.url("https://api.redoxengine.com/endpoint").setHeader("Authorization","Bearer c3773024-b383-4962-ba7c-07a394c5d3ca").setContentType("application/json").post(data);
		System.out.println("after redox request");
		return ok(json);
	}
}
