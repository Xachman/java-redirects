package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.AbstractController;
import com.gti.redirects.Answer;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Payloads.RedirectPayload;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/13/17.
 */
public class CreateRedirect extends AbstractController<RedirectPayload> {

    public CreateRedirect(Model model) {
        super(RedirectPayload.class, model);
    }

    @Override
    protected Answer processImpl(RedirectPayload value, Map queryParams, Map requestParams, boolean shouldReturnHtml) {
        Map map = new HashMap();

        Map saveVals = new HashMap();
        saveVals.put("domain", value.getDomain());
        saveVals.put("redirect_domain", value.getRedirect_domain());
        saveVals.put("status", value.getStatus());
        saveVals.put("use_path", value.getUse_path());

        List<Map<String, Object>> savedValue = model.save(saveVals);

        Map <String, Object> redirect = savedValue.get(0);
        if(shouldReturnHtml) {
            map.put("content", "create-edit-redirect/layout.hbs");
            map.put("redirect", redirect);
            headers.put("Location", "/admin/edit-redirect/"+redirect.get("id"));
            return new Answer(302, "");
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
