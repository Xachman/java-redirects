package com.gti.redirects.Redirects.Controllers;

import com.gti.redirects.AbstractController;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;

import java.util.Map;

/**
 * Created by xach on 5/23/17.
 */
public class Redirector extends AbstractController<EmptyPayload> {
    public Redirector(Class<EmptyPayload> valueClass, Model model) {
        super(valueClass, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map queryParams, Map requestParams, boolean shouldReturnHtml) {
        return null;
    }
}
