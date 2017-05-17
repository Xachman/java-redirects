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
 * Created by xach on 5/17/17.
 */
public class DeleteRedirect extends AbstractController<RedirectPayload> {

    public DeleteRedirect(Model model) {
        super(RedirectPayload.class, model);
    }

    @Override
    protected Answer processImpl(RedirectPayload value, Map queryParams, boolean shouldReturnHtml) {
        Map<String, String> map = new HashMap<>();

        boolean isDeleted = model.delete(Integer.parseInt(queryParams.get("id").toString()));

        List<Map<String, String>> savedValue = model.find();

        Map <String, String> redirect = savedValue.get(0);

        Map<String, String> outPut = new HashMap<>();
        if(shouldReturnHtml) {
            map.put("content", "create-edit-redirect/layout.hbs");
            return new Answer(200, "Location: /admin/edit-redirect/"+redirect.get("id"));
        }


        return new Answer(200, jsonObject.toJSONString());
    }
}
