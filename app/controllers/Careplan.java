package controllers;


import play.libs.ws.*;
import java.util.concurrent.CompletionStage;


import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import javax.inject.Inject;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Ebean;
import play.mvc.Http.Context;
import java.util.List;

import play.data.Form;
import play.data.DynamicForm;
import models.ebeanModel.CareplanInJson;
import models.ebeanModel.Careplans;
import models.ebeanModel.Question;
import play.libs.Json;
import java.util.Iterator;
import com.avaje.ebean.text.json.EJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

public class Careplan extends Controller {

    @Inject WSClient ws;
    public Result careplan(Long id) {
		return Results.ok(views.html.careplan.render(id));
    }

    public Result route(String text) {
		String query = "SELECT Concept_Code as value, Concept_Name as label, Code_System_OID as label1, Code_System_Name as label2 FROM route where Concept_Name like '%"+text+"%' ";
        SqlQuery sqlQuery = Ebean.createSqlQuery(query);
        List<SqlRow> list = sqlQuery.findList();
        return Results.ok(Json.toJson(list));
    }

    public Result medication(String text) {
		String query = "SELECT STR as label, CODE as value  FROM medication where STR like '%"+text+"%' ";
        SqlQuery sqlQuery = Ebean.createSqlQuery(query);
        List<SqlRow> list = sqlQuery.findList();
        return Results.ok(Json.toJson(list));
    }

    public Result save(Long id) throws java.io.IOException{
		JsonNode json = request().body().asJson();

		String formData = json.get("medicationName").toString();
		int medicationNameSize = 0;
		int booleanQuestionSize = 0;
		int multipleChoiceQuestionSize = 0;
		int freeQuestionSize = 0;

		if(json.get("medicationName")!=null)
			medicationNameSize = json.get("medicationName").size();
		if(json.get("booleanQuestion")!=null)
			booleanQuestionSize = json.get("booleanQuestion").size();
		if(json.get("multipleChoiceQuestion")!=null)
			multipleChoiceQuestionSize = json.get("multipleChoiceQuestion").size();
		if(json.get("freeQuestion")!=null)
			freeQuestionSize = json.get("freeQuestion").size();

		// patient id
		ObjectNode pid = Json.newObject();
		pid.put("id", id);

		// doctor id
		ObjectNode did = Json.newObject();
		did.put("id", ctx().session().get("id"));

		for(int i = 0; i<medicationNameSize; i++){

			ObjectNode newJson = Json.newObject();
			newJson.put("did",did);
			newJson.put("pid",pid);
			newJson.putAll((ObjectNode)json.get("medicationName").get(i).get("product"));
			newJson.putAll((ObjectNode)json.get("medicationName").get(i).get("route"));
			newJson.putAll((ObjectNode)json.get("medicationName").get(i).get("dose"));
			newJson.putAll((ObjectNode)json.get("medicationName").get(i).get("frequency"));
			newJson.putAll((ObjectNode)json.get("medicationName").get(i).get("time"));

			ObjectMapper mapper = new ObjectMapper();
			Careplans careplans = mapper.readValue(newJson.toString(), Careplans.class);
			careplans.save();

		}

		for(int i = 0; i < multipleChoiceQuestionSize; i++) {

			ObjectNode newJson = Json.newObject();
			newJson.put("did",did);
			newJson.put("pid",pid);
			newJson.put("questionNumber",json.get("multipleChoiceQuestion").get(i).get("questionNumber"));
			newJson.put("questionType","MCQ");
			newJson.put("question",json.get("multipleChoiceQuestion").get(i).get("question"));
			newJson.put("dependOnPreviousQuestion",json.get("multipleChoiceQuestion").get(i).get("dependOnPreviousQuestion"));
			newJson.put("previousQuestionAnswerShouldBe",json.get("multipleChoiceQuestion").get(i).get("previousQuestionAnswerShouldBe"));
			newJson.put("choices",json.get("multipleChoiceQuestion").get(i).get("choices").toString());

			ObjectMapper mapper = new ObjectMapper();
			Question multipleChoiceQuestion = mapper.readValue(newJson.toString(), Question.class);
			multipleChoiceQuestion.save();
		}

		for(int i = 0; i < booleanQuestionSize; i++) {

			ObjectNode newJson = Json.newObject();
			newJson.put("did",did);
			newJson.put("pid",pid);
			newJson.put("questionNumber",json.get("booleanQuestion").get(i).get("questionNumber"));
			newJson.put("questionType","boolean");
			newJson.put("question",json.get("booleanQuestion").get(i).get("question"));
			newJson.put("dependOnPreviousQuestion",json.get("booleanQuestion").get(i).get("dependOnPreviousQuestion"));
			newJson.put("previousQuestionAnswerShouldBe",json.get("booleanQuestion").get(i).get("previousQuestionAnswerShouldBe"));
			newJson.put("choices",json.get("booleanQuestion").get(i).get("choices").toString());

			ObjectMapper mapper = new ObjectMapper();
			Question booleanQuestion = mapper.readValue(newJson.toString(), Question.class);
			booleanQuestion.save();
		}

		for(int i = 0; i < freeQuestionSize; i++) {

			ObjectNode newJson = Json.newObject();
			newJson.put("did",did);
			newJson.put("pid",pid);
			newJson.put("questionNumber",json.get("freeQuestion").get(i).get("questionNumber"));
			newJson.put("questionType","free");
			newJson.put("question",json.get("freeQuestion").get(i).get("question"));
			newJson.put("dependOnPreviousQuestion",json.get("freeQuestion").get(i).get("dependOnPreviousQuestion"));
			newJson.put("previousQuestionAnswerShouldBe",json.get("freeQuestion").get(i).get("previousQuestionAnswerShouldBe"));
			
			ObjectMapper mapper = new ObjectMapper();
			Question freeQuestion = mapper.readValue(newJson.toString(), Question.class);
			freeQuestion.save();
		}
		
		List<CareplanInJson> list = Ebean.find(CareplanInJson.class)
		 .select("id")
		 .where()
		 .eq("patientid",id)
          	.findList();
          	if(list.isEmpty())
          	{
          		CareplanInJson careplaninjson = new CareplanInJson();
          		careplaninjson.medicationName=formData;
				careplaninjson.patientid=id;
				careplaninjson.save();
			}
			else
			{
				String s = "UPDATE careplan_in_json SET medication_name = '"+formData+"' where patientid = '"+id+"'";
				SqlUpdate update = Ebean.createSqlUpdate(s);
				Ebean.execute(update);
			
			}
		//careplan.patientid=id;
		

		/*String data ="{"
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
		ws.url("https://api.redoxengine.com/endpoint").setHeader("Authorization","Bearer c3773024-b383-4962-ba7c-07a394c5d3ca").setContentType("application/json").post(data);
*/		return ok(json);
	}
}
