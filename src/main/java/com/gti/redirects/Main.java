package com.gti.redirects;

import static spark.Service.ignite;

import com.github.xachman.SQLiteDatabaseHelper;
import com.gti.redirects.Redirects.Controllers.*;
import com.gti.redirects.Redirects.Models.RedirectsModel;
import com.gti.redirects.Redirects.Models.RedirectsTable;
import spark.Service;

import java.util.Base64;

public class Main {
	private static RedirectsModel redirectsModel;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		redirectsModel = new RedirectsModel(new RedirectsTable(), new SQLiteDatabaseHelper(System.getProperty("user.dir")+"/database.db"));
		startAdminInstance();
		startRedirectInstance();
	}

	static void startAdminInstance() {
		Service http = ignite().port(4000);

		http.staticFileLocation("/public");
		http.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});
		http.before("/admin/*", (request, response) -> {
			Boolean authenticated = false;
			String auth = request.headers("Authorization");
			if (auth != null && auth.startsWith("Basic")) {
				String b64Credentials = auth.substring("Basic".length()).trim();
				String credentials = new String(Base64.getDecoder().decode(b64Credentials));
				if (credentials.equals(System.getenv("USER_NAME") + ":" + System.getenv("USER_PASSWORD")))
					authenticated = true;
			}
			if (!authenticated) {
				response.header("WWW-Authenticate", "Basic realm=\"Restricted\"");
				response.status(401);
				if (!request.pathInfo().equals("/admin/login")) {
					response.redirect("/admin/login");
				}
			}
		});

		http.post("/admin/create-redirect", new CreateRedirect(redirectsModel));
		http.get("/admin/redirects", new RedirectsController(redirectsModel));
		http.get("/admin/edit-redirect/:id", new EditRedirectDisplay(redirectsModel));
		http.post("/admin/edit-redirect/:id", new EditRedirectPost(redirectsModel));
		http.post("/admin/delete-redirect/:id", new DeleteRedirect(redirectsModel));
		http.get("/admin/login", (request, response) -> "login");
	}

	static void startRedirectInstance() {
		Service http = ignite().port(3480);
		http.get("/*", new Redirector(redirectsModel));
		http.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});
	}
}
