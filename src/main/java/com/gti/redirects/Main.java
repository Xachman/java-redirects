package com.gti.redirects;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;

import com.github.xachman.SQLiteDatabaseHelper;
import com.gti.redirects.Redirects.*;
import com.gti.redirects.Redirects.Controllers.CreateRedirect;
import com.gti.redirects.Redirects.Controllers.RedirectsController;
import com.gti.redirects.Redirects.Models.RedirectsModel;
import com.gti.redirects.Redirects.Models.RedirectsTable;
import spark.Spark;

import java.util.Base64;

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
		before("/admin/*", (request, response) -> {
			Boolean authenticated = false;
			String auth = request.headers("Authorization");
			if(auth != null && auth.startsWith("Basic")) {
				String b64Credentials = auth.substring("Basic".length()).trim();
				String credentials = new String(Base64.getDecoder().decode(b64Credentials));
				if(credentials.equals(System.getenv("USER_NAME")+":"+System.getenv("USER_PASSWORD"))) authenticated = true;
			}
			if(!authenticated) {
				response.header("WWW-Authenticate", "Basic realm=\"Restricted\"");
				response.status(401);
				System.out.println(request.pathInfo());
				if(!request.pathInfo().equals("/admin/login")) {
					response.redirect("/admin/login");
				}
			}
		});

		RedirectsModel redirectsModel = new RedirectsModel(new RedirectsTable(), new SQLiteDatabaseHelper(System.getProperty("user.dir")+"/database.db"));
		get("/admin/create-redirect", RedirectsControllerOld.serveCreateRedirect);
		post("/admin/create-redirect", new CreateRedirect(redirectsModel));
		get("/admin/redirects", new RedirectsController(redirectsModel));
		get("/admin/edit-redirect/:id", RedirectsControllerOld.serveEditRedirect);
		get("/admin/delete-redirect/:id", RedirectsControllerOld.serveDeleteRedirect);
		get("/admin/login", (request, response) -> "login");
		get("/", RedirectsControllerOld.serveRedirect);
	}
}
