package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.*;
import com.gti.redirects.Redirects.Payloads.RedirectPayload;
import com.gti.redirects.Util.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/16/17.
 */
public class EditRedirectPost extends AbstractController<RedirectPayload> {
    private Map<String, Object> templateMap;
    public EditRedirectPost(Model model) {
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

        System.out.println(Integer.parseInt(queryParams.get(":id").toString()));
        List<Map<String, Object>> redirects = model.update(Integer.parseInt(queryParams.get(":id").toString()), saveVals);
        if(shouldReturnHtml) {
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
