package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.AbstractController;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Payloads.DeleteRedirectPayload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/17/17.
 */
public class DeleteRedirect extends AbstractController<DeleteRedirectPayload> {

    public DeleteRedirect(Model model ) {
        super(DeleteRedirectPayload.class, model);
    }

    @Override
    protected Answer processImpl(DeleteRedirectPayload value, Map queryParams, Map requestParams, boolean shouldReturnHtml) {
        Map<String, String> map = new HashMap<>();
        int id = Integer.parseInt(value.getId());
        boolean isDeleted = model.delete(id);

        Map<String, Object> output = new HashMap<>();


        output.put("is_deleted", isDeleted);
        output.put("id", id);

        List<Map<String, Object>> outputList = new ArrayList<>();

        outputList.add(output);
        if(shouldReturnHtml) {
            map.put("content", "create-edit-redirect/layout.hbs");
            return new Answer(200, "Location: /admin/redirects/");
        }


        return new Answer(200,dataToJson(outputList));

    }

}
