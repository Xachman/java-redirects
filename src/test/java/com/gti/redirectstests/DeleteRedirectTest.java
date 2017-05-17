package com.gti.redirectstests;

import com.gti.redirects.Answer;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Controllers.DeleteRedirect;
import com.gti.redirects.Redirects.Payloads.DeleteRedirectPayload;
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
    public void deleteRedirect() {
        DeleteRedirectPayload deletePayload = new DeleteRedirectPayload();
        deletePayload.setId("1");
        Assert.assertTrue(deletePayload.isValid());


        Model model = EasyMock.createNiceMock(Model.class);

        EasyMock.expect(model.delete(1)).andReturn(true).times(2);
        EasyMock.replay(model);

        DeleteRedirect createRedirect = new DeleteRedirect(model);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", 1);
        jsonObject.put("is_deleted", true);

        JSONArray jsonArray = new JSONArray();

        jsonArray.add(jsonObject);

        Assert.assertEquals(new Answer(200, jsonArray.toString()), createRedirect.process(deletePayload, new HashMap<>(), false));
    }

    @Test
    public void badDeleteRedirectRequest() {
        DeleteRedirectPayload deletePayload = new DeleteRedirectPayload();
        deletePayload.setId("");
        Assert.assertTrue(!deletePayload.isValid());


        Model model = EasyMock.createNiceMock(Model.class);


        DeleteRedirect createRedirect = new DeleteRedirect(model);


        Assert.assertEquals(new Answer(400), createRedirect.process(deletePayload, new HashMap<>(), false));
    }
}
