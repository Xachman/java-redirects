package com.gti.redirects.Redirects;

import com.gti.redirects.AbstractRequest;
import com.gti.redirects.Answer;
import com.gti.redirects.Model;
import com.gti.redirects.Util.ViewUtil;
import com.gti.redirects.Validable;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/13/17.
 */
public class CreateRedirect extends AbstractRequest<CreateRedirectPayload> {

    public CreateRedirect(Model model) {
        super(CreateRedirectPayload.class, model);
    }

    @Override
    protected Answer processImpl(CreateRedirectPayload value, Map queryParams, boolean shouldReturnHtml) {
        Map map = new HashMap();

        Map saveVals = new HashMap();
        saveVals.put("domain", value.getDomain());
        saveVals.put("redirect_domain", value.getRedirect_domain());
        saveVals.put("status", value.getStatus());
        saveVals.put("use_path", value.getUse_path());

        model.save(saveVals);

        List<Map<String, String>> savedValue = model.find();

        Map <String, String> redirect = savedValue.get(0);
        if(shouldReturnHtml) {
            map.put("content", "create-edit-redirect/layout.hbs");
            map.put("redirect", redirect);
            return new Answer(200, ViewUtil.render(map, "layout.hbs"));
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", redirect.get("id"));
        jsonObject.put("domain", redirect.get("domain"));
        jsonObject.put("redirect_domain", redirect.get("redirect_domain"));
        jsonObject.put("status", redirect.get("status"));
        jsonObject.put("use_path", redirect.get("use_path"));

        return new Answer(200, jsonObject.toJSONString());
    }
}
