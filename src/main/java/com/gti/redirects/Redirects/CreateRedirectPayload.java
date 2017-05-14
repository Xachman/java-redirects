package com.gti.redirects.Redirects;

import com.gti.redirects.Validable;

/**
 * Created by xach on 5/13/17.
 */
public class CreateRedirectPayload implements Validable {
    private String domain;
    private String status;
    private String redirect_domain;
    private int use_path;

    public boolean isValid() {
        return domain != null && !domain.isEmpty() &&
                status != null && !status.isEmpty() &&
                redirect_domain != null && !redirect_domain.isEmpty();
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRedirect_domain(String redirect_domain) {
        this.redirect_domain = redirect_domain;
    }

    public void setUse_path(int use_path) {
        this.use_path = use_path;
    }

    public String getDomain() {
        return domain;
    }

    public String getStatus() {
        return status;
    }

    public String getRedirect_domain() {
        return redirect_domain;
    }

    public int getUse_path() {
        return use_path;
    }
}
