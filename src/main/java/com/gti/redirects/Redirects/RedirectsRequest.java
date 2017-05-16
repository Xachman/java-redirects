package com.gti.redirects.Redirects;

import com.gti.redirects.AbstractRequest;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Util.ViewUtil;
import com.gti.redirects.Validable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/13/17.
 */
public class RedirectsRequest<V extends Validable> extends AbstractRequest {
    private Map <String, Object> templateMap;
    public RedirectsRequest(RedirectsModel model) {
       super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(Validable value, Map queryParams, boolean shouldReturnHtml) {
        if(shouldReturnHtml) {
            List redirects = model.find();
            Map map = new HashMap();
            map.put("redirects", redirects);
            map.put("content", "redirects/layout.hbs");
            return Answer.ok(ViewUtil.render(map, "layout.hbs"));
        } else {
            String json = dataToJson(model);
            return Answer.ok(json);
        }
    }
}
