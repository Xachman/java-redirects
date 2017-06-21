package com.gti.redirectstests;

import com.github.xachman.Column;
import com.github.xachman.Entry;
import com.github.xachman.Row;
import com.gti.redirects.Answer;
import com.gti.redirects.EmptyPayload;
import com.gti.redirects.Model;
import com.gti.redirects.Redirects.Controllers.Redirector;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by xach on 5/24/17.
 */
public class RedirectorTest {
    private List<Entry> entries;
    private List<Entry> entries2;
    private List<Entry> entries3;
    private Row row;
    private Row row2;
    private Row row3;

    @Before
    public void beforeAll() {

        entries = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "1"),
                new Entry(new Column("text", "domain"), "test.com"),
                new Entry(new Column("text", "redirect_domain"), "test2.com"),
                new Entry(new Column("text", "status"), "301"),
                new Entry(new Column("int", "use_path"), "0")
        ));
        row = new Row(entries);

        entries2 = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "2"),
                new Entry(new Column("text", "domain"), "test2.com"),
                new Entry(new Column("text", "redirect_domain"), "test3.com"),
                new Entry(new Column("text", "status"), "404"),
                new Entry(new Column("int", "use_path"), "1")
        ));
        row2 = new Row(entries2);

        entries3 = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "3"),
                new Entry(new Column("text", "domain"), "test4.com"),
                new Entry(new Column("text", "redirect_domain"), "test4.com"),
                new Entry(new Column("text", "status"), "401"),
                new Entry(new Column("int", "use_path"), "0")
        ));
        row3 = new Row(entries3);

    }


    @Test
    public void redirect301() {
        EmptyPayload emptyPayload = new EmptyPayload();
        Assert.assertTrue(emptyPayload.isValid());

        Map<String, String> request = new HashMap<>();
        request.put("host", "test.com");

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("id", "1");
        returnMap.put("domain", "test.com");
        returnMap.put("redirect_domain", "test2.com");
        returnMap.put("status", "301");
        returnMap.put("use_path", "0");

        Model model = EasyMock.createNiceMock(Model.class);
        EasyMock.expect(model.find(EasyMock.isA(List.class))).andReturn(new ArrayList<>(Arrays.asList(returnMap)));
        EasyMock.replay(model);

        Redirector redirector = new Redirector(model);


        Assert.assertEquals(new Answer(301, ""), redirector.process(emptyPayload, new HashMap<>(), request, false));

        Assert.assertEquals(redirector.getHeaders().get("Location"), "http://test2.com");

        EasyMock.verify(model);
    }
    @Test
    public void redirectWithPath() {
        EmptyPayload emptyPayload = new EmptyPayload();
        Assert.assertTrue(emptyPayload.isValid());

        Map<String, String> request = new HashMap<>();
        request.put("host", "test.com");
        request.put("path", "/test/test");

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("id", "1");
        returnMap.put("domain", "test.com");
        returnMap.put("redirect_domain", "test2.com");
        returnMap.put("status", "301");
        returnMap.put("use_path", "1");

        Model model = EasyMock.createNiceMock(Model.class);
        EasyMock.expect(model.find(EasyMock.isA(List.class))).andReturn(new ArrayList<>(Arrays.asList(returnMap)));
        EasyMock.replay(model);

        Redirector redirector = new Redirector(model);


        Assert.assertEquals(new Answer(301, ""), redirector.process(emptyPayload, new HashMap<>(), request, false));

        Assert.assertEquals(redirector.getHeaders().get("Location"), "http://test2.com");

        EasyMock.verify(model);
    }
}
