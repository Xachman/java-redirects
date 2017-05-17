package com.gti.redirectstests;

import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Controllers.EditRedirectDisplay;
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
 * Created by xach on 5/16/17.
 */
public class EditRedirectTest {
    private List<Map<String, Object>> returnFind = new ArrayList<>();

    @Before
    public void beforeAll() {
        Map<String, Object> returnHash = new HashMap<>();
        returnHash.put("id", "3");
        returnHash.put("domain", "domain.com");
        returnHash.put("redirect_domain", "newdomain.com");
        returnHash.put("status", "301");
        returnHash.put("use_path", "1");

        returnFind.add(returnHash);
    }

    @Test
    public void displayForm() {
        EmptyPayload emptyPayload = new EmptyPayload();
        Assert.assertTrue(emptyPayload.isValid());

        Model model = EasyMock.createNiceMock(Model.class);
        EasyMock.expect(model.find(3)).andReturn(returnFind);
        EasyMock.replay(model);

        EditRedirectDisplay editRedirectDisplay = new EditRedirectDisplay(model);

        Map<String, String> map = new HashMap<>();
        map.put("id", "3");

        JSONArray jsonArray = new JSONArray();
        for(Map<String, Object> mapFind : returnFind) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", mapFind.get("id"));
            jsonObject.put("domain", mapFind.get("domain"));
            jsonObject.put("redirect_domain", mapFind.get("redirect_domain"));
            jsonObject.put("use_path", mapFind.get("use_path"));
            jsonObject.put("status", mapFind.get("status"));

            jsonArray.add(jsonObject);

        }

        Assert.assertEquals(new Answer(200 , jsonArray.toString()), editRedirectDisplay.process(emptyPayload, map, false));

    }
}
