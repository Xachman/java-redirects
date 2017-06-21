package com.gti.redirects.Util;

import org.json.simple.JSONObject;

/**
 * Created by xach on 5/14/17.
 */
public class QueryParamUtil {
    private String queryString;
    public QueryParamUtil(String queryString) {
        this.queryString = queryString;
    }

    public String toJson() {
        String[] parts = queryString.split("&");

        JSONObject json = new JSONObject();

        if(queryString.trim().length() > 0) {
            for (String part : parts) {
                String[] keyVal = part.split("="); // The equal separates key and values
                String val = "";
                if(keyVal.length > 1) {
                    val = keyVal[1];
                }
                json.put(keyVal[0], val);
            }
        }

        return json.toString();
    }
}
