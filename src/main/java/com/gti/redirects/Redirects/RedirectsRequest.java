package com.gti.redirects.Redirects;

import com.gti.redirects.*;
import com.gti.redirects.Util.ViewUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/13/17.
 */
public class RedirectsRequest<V extends Validable> extends AbstractRequest {
    private Map <String, Object> templateMap;
    public RedirectsRequest(Model model) {
       super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(Validable value, Map queryParams, boolean shouldReturnHtml) {
        List<Map<String,String>> redirects = model.find();
        if(shouldReturnHtml) {
            Map map = new HashMap();
            map.put("redirects", redirects);
            map.put("content", "redirects/layout.hbs");
            return Answer.ok(ViewUtil.render(map, "layout.hbs"));
        } else {

            JSONArray jsonArray = new JSONArray();

            for(Map<String, String> map : redirects) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", map.get("id"));
                jsonObject.put("domain", map.get("domain"));
                jsonObject.put("redirect_domain", map.get("redirect_domain"));
                jsonObject.put("use_path", map.get("use_path"));
                jsonObject.put("status", map.get("status"));

                jsonArray.add(jsonObject);
            }
            return Answer.ok(jsonArray.toString());
        }
    }
}
