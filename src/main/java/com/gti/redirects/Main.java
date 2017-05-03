package com.gti.redirects;

import static spark.Spark.get;
import static spark.Spark.post;

import com.gti.redirects.Redirects.RedirectStorage;
import com.gti.redirects.Redirects.RedirectsController;
import spark.Spark;

public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Spark.port(4000);
		Spark.staticFileLocation("/public");
		RedirectStorage redirectStorage = new RedirectStorage();
		Spark.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});
		get("/admin/create-redirect", RedirectsController.serveCreateRedirect);
		post("/admin/create-redirect", RedirectsController.serveCreateRedirectPost);
		get("/admin/redirects", RedirectsController.serveRedirects);
		get("/admin/edit-redirect/:id", RedirectsController.serveEditRedirect);
		get("/admin/delete-redirect/:id", RedirectsController.serveDeleteRedirect);
		get("/", (req, res) -> "Hello World!");
	}
}
