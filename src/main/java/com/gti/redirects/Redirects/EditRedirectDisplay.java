package com.gti.redirects.Redirects;

import com.gti.redirects.*;
import com.gti.redirects.Util.ViewUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/16/17.
 */
public class EditRedirectDisplay extends AbstractRequest {
    private Map<String, Object> templateMap;
    public EditRedirectDisplay(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(Validable value, Map queryParams, boolean shouldReturnHtml) {
        List<Map<String,String>> redirects = model.find(Integer.parseInt(queryParams.get("id").toString()));
        if(shouldReturnHtml) {
            Map map = new HashMap();
            map.put("redirect", redirects);
            map.put("content", "redirects/layout.hbs");
            return Answer.ok(ViewUtil.render(map, "layout.hbs"));
        } else {

            return Answer.ok(dataToJson(redirects));
        }
    }
}
