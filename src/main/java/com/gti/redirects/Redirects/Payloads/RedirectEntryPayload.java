package com.gti.redirects.Redirects.Payloads;

import com.gti.redirects.Validable;

/**
 * Created by xach on 5/13/17.
 */
public class RedirectEntryPayload extends RedirectPayload{
    private String id;

    @Override
    public boolean isValid() {
        return domain != null && !domain.isEmpty() &&
                status != null && !status.isEmpty() &&
                redirect_domain != null && !redirect_domain.isEmpty() &&
                validInt(id);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
