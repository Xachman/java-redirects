package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.AbstractController;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Util.ViewUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    private Map getRedirectByDomain(String domain) {
        List<Map<String, Object>> redirects = model.find();
        System.out.println(domain);
        for (Map redirect : redirects) {
            String redirectDomain = redirect.get("domain").toString();
            if(redirectDomain.equals(domain)) {
                return redirect;
            }
        }

        return null;
    }
}
