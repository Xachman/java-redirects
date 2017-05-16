package com.gti.redirects.Redirects;

import com.gti.redirects.*;
import com.gti.redirects.Util.ViewUtil;
import com.sun.org.apache.regexp.internal.RE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/16/17.
 */
public class EditRedirectPost extends AbstractRequest<RedirectPayload> {
    private Map<String, Object> templateMap;
    public EditRedirectPost(Model model) {
        super(RedirectPayload.class, model);
    }

    @Override
    protected Answer processImpl(RedirectPayload value, Map queryParams, boolean shouldReturnHtml) {
        Map map = new HashMap();

        Map saveVals = new HashMap();
        saveVals.put("domain", value.getDomain());
        saveVals.put("redirect_domain", value.getRedirect_domain());
        saveVals.put("status", value.getStatus());
        saveVals.put("use_path", value.getUse_path());

        model.save(saveVals);
        List<Map<String,String>> redirects = model.find(Integer.parseInt(queryParams.get("id").toString()));
        if(shouldReturnHtml) {
            map.put("redirect", redirects);
            map.put("content", "redirects/layout.hbs");
            return Answer.ok(ViewUtil.render(map, "layout.hbs"));
        } else {

            return Answer.ok(dataToJson(redirects));
        }
    }
}
