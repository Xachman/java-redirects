package com.gti.redirects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;

import com.gti.redirects.Redirects.RedirectsController;
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
		get("/admin/create-redirect", RedirectsController.serveCreateRedirect);
		post("/admin/create-redirect", RedirectsController.serveCreateRedirectPost);
		get("/admin/redirects", RedirectsController.serveRedirects);
		get("/admin/edit-redirect/:id", RedirectsController.serveEditRedirect);
		get("/", (req, res) -> "Hello World!");
	}
}
