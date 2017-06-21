package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.AbstractController;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 6/21/17.
 */
public class CreateRedirectDisplay extends AbstractController<EmptyPayload> {
    private Map<String, Object> templateMap;
    public CreateRedirectDisplay(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map queryParams, Map requestParams, boolean shouldReturnHtml) {
        if(shouldReturnHtml) {
            Map map = new HashMap();
            map.put("title", "Create Redirect");
            map.put("content", "create-edit-redirect/layout.hbs");
            return Answer.ok(ViewUtil.render(map, "layout.hbs"));
        } else {

            return Answer.ok("{}");
        }
    }
}
