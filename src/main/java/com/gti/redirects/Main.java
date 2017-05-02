package com.gti.redirects;

import java.util.HashMap;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Spark.port(4000);
		Spark.staticFileLocation("/public");
		Storage storage = new Storage();
		Spark.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});
		get("/create-redirect", (req, res) -> {

			Map map = new HashMap();
			map.put("content", "create-redirect/layout.hbs");
			return new ModelAndView(map, "layout.hbs");
		}, new HandlebarsTemplateEngine());
		post("/create-redirect", (req, res) -> {
			System.out.println(req.queryParams());
			Redirect redirect = new Redirect(req.queryParams("domain"), req.queryParams("type"), req.queryParams("redirect_to"));
			storage.addRedirect(redirect);
			Map map = new HashMap();
			map.put("content", "create-redirect/layout.hbs");
			map.put("redirect", redirect.toMap());
			return new ModelAndView(map, "layout.hbs");
		}, new HandlebarsTemplateEngine());
		get("/redirects", (req, res) -> "Hello World!");
		get("/", (req, res) -> "Hello World!");
	}
}
