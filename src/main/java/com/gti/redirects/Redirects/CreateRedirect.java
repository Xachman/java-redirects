package com.gti.redirects.Redirects;

import com.gti.redirects.AbstractRequest;
import com.gti.redirects.Answer;
import com.gti.redirects.Model;
import com.gti.redirects.Util.ViewUtil;
import com.gti.redirects.Validable;

import java.util.HashMap;
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
        map.put("content", "create-edit-redirect/layout.hbs");
        System.out.println(value.getDomain());
        System.out.println(queryParams);
        return new Answer(200, ViewUtil.render(map, "layout.hbs"));
    }
}
