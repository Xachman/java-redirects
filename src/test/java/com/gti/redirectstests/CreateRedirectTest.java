package com.gti.redirectstests;

import com.gti.redirects.Answer;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.CreateRedirect;
import com.gti.redirects.Redirects.CreateRedirectPayload;
import com.gti.redirects.Redirects.RedirectsModel;
import com.gti.redirects.Validable;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.easymock.EasyMock;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by xach on 5/14/17.
 */
public class CreateRedirectTest {
    @Test
    public void createsRedirect() {
        CreateRedirectPayload crPayload = new CreateRedirectPayload();
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

        EasyMock.expect(model.save(saveHash)).andReturn(true);
        EasyMock.expect(model.find()).andReturn(findReturn);
        EasyMock.replay(model);

        CreateRedirect createRedirect = new CreateRedirect(model);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", returnHash.get("id"));
        jsonObject.put("domain", returnHash.get("domain"));
        jsonObject.put("redirect_domain", returnHash.get("redirect_domain"));
        jsonObject.put("status", returnHash.get("status"));
        jsonObject.put("use_path", returnHash.get("use_path"));

        assertEquals(new Answer(200, jsonObject.toJSONString()), createRedirect.process(crPayload, new HashMap<>(), false));
    }
}
