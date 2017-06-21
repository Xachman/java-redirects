package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.AbstractController;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Util.ViewUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xach on 6/21/17.
 */
public class Dashboard extends AbstractController<EmptyPayload> {

    public Dashboard(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String, String> queryParams, Map<String, String> requestParams, boolean shouldReturnHtml) {
        if(shouldReturnHtml) {
            Map map = new HashMap();
            map.put("title", "Dashboard");
            map.put("content", "dashboard/layout.hbs");
            return Answer.ok(ViewUtil.render(map, "layout.hbs"));
        }
        return null;
    }
}
