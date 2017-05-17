package com.gti.redirectstests;

import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Controllers.RedirectsController;
import org.easymock.EasyMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/15/17.
 */
public class RedirectsRequestTest {
    private List<Map<String, Object>> returnFind = new ArrayList<>();


    @Before
    public void beforeAll() {
        Map<String, Object> returnHash = new HashMap<>();
        returnHash.put("id", "1");
        returnHash.put("domain", "domain.com");
        returnHash.put("redirect_domain", "newdomain.com");
        returnHash.put("status", "301");
        returnHash.put("use_path", "1");

        returnFind.add(returnHash);


        Map<String, Object> returnHash2 = new HashMap<>();
        returnHash.put("id", "2");
        returnHash.put("domain", "domain2.com");
        returnHash.put("redirect_domain", "newdomain2.com");
        returnHash.put("status", "302");
        returnHash.put("use_path", "0");

        returnFind.add(returnHash2);


        Map<String, Object> returnHash3 = new HashMap<>();
        returnHash.put("id", "3");
        returnHash.put("domain", "domain3.com");
        returnHash.put("redirect_domain", "newdomain3.com");
        returnHash.put("status", "404");
        returnHash.put("use_path", "0");

        returnFind.add(returnHash2);
    }

    @Test
    public void displayRedirects() {
        EmptyPayload emptyPayload = new EmptyPayload();
        Assert.assertTrue(emptyPayload.isValid());

        Model model = EasyMock.createNiceMock(Model.class);
        EasyMock.expect(model.find()).andReturn(returnFind);
        EasyMock.replay(model);

        RedirectsController redirectsRequest = new RedirectsController(model);

        JSONArray jsonArray = new JSONArray();

        for(Map<String, Object> map : returnFind) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", map.get("id"));
            jsonObject.put("domain", map.get("domain"));
            jsonObject.put("redirect_domain", map.get("redirect_domain"));
            jsonObject.put("use_path", map.get("use_path"));
            jsonObject.put("status", map.get("status"));

            jsonArray.add(jsonObject);
        }

        Assert.assertEquals(new Answer(200 , jsonArray.toString()), redirectsRequest.process(emptyPayload, new HashMap<>(), false));
    }
}
