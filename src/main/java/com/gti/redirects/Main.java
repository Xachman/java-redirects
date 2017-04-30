package com.gti.redirects;

import java.util.HashMap;
import java.util.Map;
import static spark.Spark.get;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Spark.staticFileLocation("/public");
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
			Map map = new HashMap();
			map.put("content", "create-redirect/layout.hbs");
			return new ModelAndView(map, "layout.hbs");
		}, new HandlebarsTemplateEngine());
		get("/redirects", (req, res) -> "Hello World!");
		get("/", (req, res) -> "Hello World!");
	}
}
