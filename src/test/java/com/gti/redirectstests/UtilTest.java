package com.gti.redirectstests;

import com.gti.redirects.Util.QueryParamUtil;
import com.sun.xml.internal.ws.developer.UsesJAXBContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by xach on 5/14/17.
 */
public class UtilTest {

    @Test
    public void queryStringToJson() {
        String queryString = "test=val1&test2=val2&test3=val3";


        String expected = "{\"test\":\"val1\",\"test2\":\"val2\",\"test3\":\"val3\"}";

        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(expected);
            QueryParamUtil qUtil = new QueryParamUtil(queryString);
            Assert.assertEquals(json.toString(), qUtil.toJson());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
