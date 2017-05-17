package com.gti.redirects.Redirects.Payloads;

import com.gti.redirects.AbstractValidable;
import com.gti.redirects.Validable;

/**
 * Created by xach on 5/17/17.
 */
public class DeleteRedirectPayload extends AbstractValidable {
    private String id;

    @Override
    public boolean isValid() {
        return validInt(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
