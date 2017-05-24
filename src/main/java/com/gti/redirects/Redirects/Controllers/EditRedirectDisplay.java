package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.*;
import com.gti.redirects.Util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/16/17.
 */
public class EditRedirectDisplay extends AbstractController<EmptyPayload> {
    private Map<String, Object> templateMap;
    public EditRedirectDisplay(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map queryParams, Map requestParams, boolean shouldReturnHtml) {
        int id = Integer.parseInt(queryParams.get(":id").toString());
        List<Map<String, Object>> redirects = model.find(id);
        if(shouldReturnHtml) {
            Map map = new HashMap();
            map.put("title", "Edit Redirect");
            redirects.get(0).put("use_path_"+redirects.get(0).get("use_path").toString(), "1");
            map.put("redirect", redirects.get(0));
            map.put("content", "create-edit-redirect/layout.hbs");
            return Answer.ok(ViewUtil.render(map, "layout.hbs"));
        } else {

            return Answer.ok(dataToJson(redirects));
        }
    }
}
