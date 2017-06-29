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
		int port = System.getenv("AMDMIN_PORT") != null && System.getenv("ADMIN_PORT").length() > 0 && Integer.parseInt(System.getenv("ADMIN_PORT")) > 0? Integer.parseInt(System.getenv("ADMIN_PORT")) : 4000;
		System.out.println("Admin port is: "+port);
		Service http = ignite().port(port);

		http.staticFileLocation("/public");
		http.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});
		http.before("/*", (request, response) -> {
			Boolean authenticated = false;
			String auth = request.headers("Authorization");
			if (auth != null && auth.startsWith("Basic")) {
				String b64Credentials = auth.substring("Basic".length()).trim();
				String credentials = new String(Base64.getDecoder().decode(b64Credentials));
				if (credentials.equals(System.getenv("USER_NAME") + ":" + System.getenv("USER_PASSWORD")))
					authenticated = true;
			}
			System.out.println("authenticated: "+ authenticated);
			System.out.println("auth: "+auth);
			if (!authenticated) {
				response.header("WWW-Authenticate", "Basic realm=\"Restricted\"");
				response.status(401);
				if (!request.pathInfo().equals("/admin/login")) {
					response.redirect("/admin/login");
				}
			}
		});

		http.post("/admin/create-redirect", new CreateRedirect(redirectsModel));
		http.get("/admin/create-redirect", new CreateRedirectDisplay(redirectsModel));
		http.get("/admin/redirects", new RedirectsController(redirectsModel));
		http.get("/admin/edit-redirect/:id", new EditRedirectDisplay(redirectsModel));
		http.post("/admin/edit-redirect/:id", new EditRedirectPost(redirectsModel));
		http.post("/admin/delete-redirect/:id", new DeleteRedirect(redirectsModel));
		http.get("/admin", new Dashboard(redirectsModel));
		http.get("/admin/login", (request, response) -> "login");
	}

	static void startRedirectInstance() {
		int port = System.getenv("REDIRECT_PORT") != null && System.getenv("REDIRECT_PORT").length() > 0 && Integer.parseInt(System.getenv("REDIRECT_PORT")) > 0? Integer.parseInt(System.getenv("REDIRECT_PORT")) : 8080;
		System.out.println("Redirect port is: "+port);
		Service http = ignite().port(port);
		http.get("/*", new Redirector(redirectsModel));
		http.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});
	}
}
