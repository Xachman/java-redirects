package com.gti.redirects.Redirects;

import com.gti.redirects.Redirect;
import com.gti.redirects.Storage;
import com.gti.redirects.StorageI;
import com.gti.redirects.Util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/2/17.
 */
public class RedirectsController {
    public static Route serveCreateRedirect = (Request request, Response response) -> {
        Map map = new HashMap();
        map.put("content", "create-edit-redirect/layout.hbs");
        map.put("post_url", "/admin/create-redirect");
        return ViewUtil.render(request, map, "layout.hbs");
    };
    public static Route serveCreateRedirectPost = (Request request, Response response) -> {
        Redirect redirect = new Redirect(request.queryParams("domain"), request.queryParams("type"), request.queryParams("redirect_to"));
        StorageI storage = new Storage();
        storage.addRedirect(redirect);
        Map map = new HashMap();
        map.put("content", "create-edit-redirect/layout.hbs");
        map.put("redirect", redirect.toMap());
        return ViewUtil.render(request, map, "layout.hbs");
    };
    public static Route serveRedirects = (Request request, Response response) -> {
        StorageI storage = new Storage();
        List redirects = storage.redirects();
        Map map = new HashMap();
        map.put("redirects", redirects);
        map.put("content", "redirects/layout.hbs");
        return ViewUtil.render(request, map, "layout.hbs");
    };
    public static Route serveEditRedirect = (Request request, Response response) -> {
        StorageI storage = new Storage();
        Map redirect = storage.redirects().get(Integer.parseInt(request.params(":id").toString()));
        Map map = new HashMap();
        map.put("redirect", redirect);
        map.put("content", "create-edit-redirect/layout.hbs");
        return ViewUtil.render(request, map, "layout.hbs");
    };
}
