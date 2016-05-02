package controllers;

import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;

public class Dashboard extends Controller {

    public Result search() {

		return Results.ok(views.html.search.render());
    }
}