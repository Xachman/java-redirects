package com.gti.redirectstests;

import com.github.xachman.Column;
import com.gti.redirects.Answer;
import com.gti.redirects.Database.RedirectsTable;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Controllers.DeleteRedirect;
import com.gti.redirects.Redirects.Payloads.DeleteRedirectPayload;
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

    /**
     * Created by xach on 5/10/17.
     */
    public static class RedirectsTableTest {
        @Test
        public void testColumns() {
            List<Column> columns = new ArrayList<>(Arrays.asList(
                new Column("integer", "id", true, true),
                new Column("text", "domain"),
                new Column("int", "status"),
                new Column("text","redirect_to"),
                new Column("int", "follow_path")
            ));

            Assert.assertEquals(new RedirectsTable().columns().size(), columns.size());

            int count = 0;
            for(Column column: columns) {
                Assert.assertEquals(new RedirectsTable().columns().get(count).name(), column.name());
                Assert.assertEquals(new RedirectsTable().columns().get(count).type(), column.type());
                count++;
            }
        }
    }
}
