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

        for(String part: parts){
            String[] keyVal = part.split("="); // The equal separates key and values
            json.put(keyVal[0], keyVal[1]);
        }

        return json.toString();
    }
}
