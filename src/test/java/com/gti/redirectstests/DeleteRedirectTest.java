package com.gti.redirectstests;

import com.gti.redirects.Answer;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Controllers.DeleteRedirect;
import com.gti.redirects.Redirects.Payloads.RedirectPayload;
import org.easymock.EasyMock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by xach on 5/17/17.
 */
public class DeleteRedirectTest {
    @Test
    public void createsRedirect() {
        RedirectEntryPayload crPayload = new RedirectEntryPayload();
        crPayload.setDomain("domain.com");
        crPayload.setRedirect_domain("newdomain.com");
        crPayload.setStatus("301");
        crPayload.setUse_path(1);
        Assert.assertTrue(crPayload.isValid());

        Map<String, String> returnHash = new HashMap<>();
        returnHash.put("id", "1");
        returnHash.put("domain", "domain.com");
        returnHash.put("redirect_domain", "newdomain.com");
        returnHash.put("status", "301");
        returnHash.put("use_path", "1");
        List<Map<String,String>> findReturn = new ArrayList<>(Arrays.asList(returnHash));

        Map<String, String> saveHash = new HashMap<>();

        saveHash.put("domain","domain.com");
        saveHash.put("redirect_domain", "newdomain.com");
        saveHash.put("status", "301");
        saveHash.put("use_path", "1");


        Model model = EasyMock.createNiceMock(Model.class);

        EasyMock.expect(model.save(saveHash)).andReturn(true).times(2);
        EasyMock.expect(model.find()).andReturn(findReturn).times(2);
        EasyMock.replay(model);

        DeleteRedirect createRedirect = new DeleteRedirect(model);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", returnHash.get("id"));
        jsonObject.put("is_deleted", true);

        JSONArray jsonArray = new JSONArray();

        jsonArray.add(jsonObject);

        Assert.assertEquals(new Answer(200, jsonArray.toString()), createRedirect.process(crPayload, new HashMap<>(), false));
    }

    @Test
    public void badRedirectPost() {
        RedirectPayload crPayload = new RedirectPayload();
        crPayload.setDomain("");
        crPayload.setRedirect_domain("newdomain.com");
        crPayload.setStatus("301");
        crPayload.setUse_path(1);
        Assert.assertTrue(!crPayload.isValid());

        Map<String, String> returnHash = new HashMap<>();
        returnHash.put("id", "1");
        returnHash.put("domain", "domain.com");
        returnHash.put("redirect_domain", "newdomain.com");
        returnHash.put("status", "301");
        returnHash.put("use_path", "1");
        List<Map<String,String>> findReturn = new ArrayList<>(Arrays.asList(returnHash));


        Model model = EasyMock.createNiceMock(Model.class);


        DeleteRedirect createRedirect = new DeleteRedirect(model);


        Assert.assertEquals(new Answer(400), createRedirect.process(crPayload, new HashMap<>(), false));
    }
}
