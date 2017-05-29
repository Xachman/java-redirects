package com.gti.redirects.Redirects.Controllers;

import com.github.xachman.Where.Comparison;
import com.github.xachman.Where.Condition;
import com.github.xachman.Where.Where;
import com.gti.redirects.AbstractController;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Util.ViewUtil;

import java.util.*;

/**
 * Created by xach on 5/23/17.
 */
public class Redirector extends AbstractController<EmptyPayload> {
    public Redirector(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map queryParams, Map requestParams, boolean shouldReturnHtml) {
        Map redirect = getRedirectByDomain(requestParams.get("host").toString());
        System.out.println(requestParams.get("pathInfo"));
        int status = Integer.parseInt(redirect.get("status").toString());
        String redirectLocation = "http://"+redirect.get("redirect_domain").toString();
        if(status > 400 && status < 5000) {
            Map<String, Object> map = new HashMap<>();
            map.put("redirectLocation", redirectLocation);
            return new Answer(status, ViewUtil.render(map, "layout4xx.hbs"));
        }

        headers.put("Location", redirectLocation);

        return new Answer(Integer.parseInt(redirect.get("status").toString()), "");
    }


    private Map<String, Object> getRedirectByDomain(String domain) {
        List<Map<String, String>> maps = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("column", "domain");
        map.put("operator", "=");
        map.put("value", domain);
        maps.add(map);
        List<Map<String, Object>> redirects = model.find(maps);

        return redirects.get(0);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
