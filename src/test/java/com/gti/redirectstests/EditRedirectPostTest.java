package com.gti.redirectstests;

import com.gti.redirects.Answer;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Controllers.EditRedirectPost;
import com.gti.redirects.Redirects.Payloads.RedirectPayload;
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
public class EditRedirectPostTest {
    private List<Map<String, String>> returnFind = new ArrayList<>();

    @Before
    public void beforeAll() {
        Map<String, String> returnHash = new HashMap<>();
        returnHash.put("id", "3");
        returnHash.put("domain", "domain.com");
        returnHash.put("redirect_domain", "newdomain.com");
        returnHash.put("status", "301");
        returnHash.put("use_path", "0");

        returnFind.add(returnHash);
    }

    @Test
    public void displayForm() {
        RedirectPayload  payload = new RedirectPayload();
        payload.setDomain("domain.com");
        payload.setRedirect_domain("newdomain.com");
        payload.setStatus("301");
        payload.setUse_path(0);
        Assert.assertTrue(payload.isValid());

        Model model = EasyMock.createNiceMock(Model.class);
        EasyMock.expect(model.save(returnFind.get(0))).andReturn(true);
        EasyMock.expect(model.find(3)).andReturn(returnFind);
        EasyMock.replay(model);

        EditRedirectPost editRedirectPost = new EditRedirectPost(model);

        Map<String, String> map = new HashMap<>();
        map.put("id", "3");

        JSONArray jsonArray = new JSONArray();
        for(Map<String, String> mapFind : returnFind) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", mapFind.get("id"));
            jsonObject.put("domain", mapFind.get("domain"));
            jsonObject.put("redirect_domain", mapFind.get("redirect_domain"));
            jsonObject.put("use_path", mapFind.get("use_path"));
            jsonObject.put("status", mapFind.get("status"));

            jsonArray.add(jsonObject);

        }

        Assert.assertEquals(new Answer(200 , jsonArray.toString()), editRedirectPost.process(payload, map, false));

    }
}
