package com.gti.redirectstests;

import com.github.xachman.*;
import com.gti.redirects.Redirects.Models.RedirectsModel;
import com.gti.redirects.Redirects.Models.RedirectsTable;
import com.gti.redirectstests.Database.Mocks.MockTable;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by xach on 5/17/17.
 */
public class RedirectsModelTest {
    private String dbPath = System.getProperty("user.dir")+"/src/test/mock/database.db";
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
    public void saveRedirect() {
        MockTable table = new MockTable();

        Map<String, Object> map = new HashMap<>();
        map.put("domain", "test.com");
        map.put("redirect_domain", "test2.com");
        map.put("status", "301");
        map.put("use_path", "0");

        List<Entry> entries = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "1"),
                new Entry(new Column("text", "domain"), "test.com"),
                new Entry(new Column("text", "redirect_domain"), "test2.com"),
                new Entry(new Column("text", "status"), "301"),
                new Entry(new Column("int", "use_path"), "0")
        ));
        Row row = new Row(entries);

        SQLiteDatabaseHelper dbh = EasyMock.createNiceMock(SQLiteDatabaseHelper.class);
        dbh.open();
        EasyMock.expectLastCall();

        Map<String, String> mapInsert = new HashMap<>();
        mapInsert.put("domain", "test.com");
        mapInsert.put("status", "301");
        mapInsert.put("redirect_domain", "test2.com");
        mapInsert.put("use_path", "0");

        EasyMock.expect(dbh.insert(table, mapInsert)).andReturn(row);
        dbh.close();
        EasyMock.expectLastCall();

        EasyMock.replay(dbh);

        RedirectsModel model = new RedirectsModel(table, dbh);
        List<Map<String, Object>> save = model.save(map);



        EasyMock.verify(dbh);

        Assert.assertEquals("test.com", save.get(0).get("domain").toString());
        Assert.assertEquals("301", save.get(0).get("status").toString());
        Assert.assertEquals("test2.com", save.get(0).get("redirect_domain").toString());
        Assert.assertEquals("0", save.get(0).get("use_path").toString());
    }

    @Test
    public void findAll() {
        MockTable table = new MockTable();

        Map<String, Object> map = new HashMap<>();
        map.put("domain", "test.com");
        map.put("redirect_domain", "test2.com");
        map.put("status", "301");
        map.put("use_path", "0");

        List<Row> rows = new ArrayList<>();

        rows.add(row);
        rows.add(row2);
        rows.add(row3);

        SQLiteDatabaseHelper dbh = EasyMock.createNiceMock(SQLiteDatabaseHelper.class);
        dbh.open();
        EasyMock.expectLastCall();
        EasyMock.expect(dbh.getRows(table)).andReturn(rows);
        dbh.close();
        EasyMock.expectLastCall();

        EasyMock.replay(dbh);

        RedirectsModel model = new RedirectsModel(table, dbh);
        List<Map<String, Object>> returnList = model.find();

        EasyMock.verify(dbh);
        Assert.assertEquals(returnList.get(0).get("id").toString(), row.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("domain").toString(), row.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("redirect_domain").toString(), row.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("status").toString(), row.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("use_path").toString(), row.getEntry(4).getValue().toString());

        Assert.assertEquals(returnList.get(1).get("id").toString(), row2.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("domain").toString(), row2.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("redirect_domain").toString(), row2.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("status").toString(), row2.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("use_path").toString(), row2.getEntry(4).getValue().toString());

        Assert.assertEquals(returnList.get(2).get("id").toString(), row3.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("domain").toString(), row3.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("redirect_domain").toString(), row3.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("status").toString(), row3.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("use_path").toString(), row3.getEntry(4).getValue().toString());
    }

    @Test
    public void findById() {
        MockTable table = new MockTable();

        Map<String, Object> map = new HashMap<>();
        map.put("domain", "test.com");
        map.put("redirect_domain", "test2.com");
        map.put("status", "301");
        map.put("use_path", "0");

        SQLiteDatabaseHelper dbh = EasyMock.createNiceMock(SQLiteDatabaseHelper.class);
        dbh.open();
        EasyMock.expectLastCall();
        EasyMock.expect(dbh.getRowById(table, 2)).andReturn(row2);
        dbh.close();
        EasyMock.expectLastCall();

        EasyMock.replay(dbh);

        RedirectsModel model = new RedirectsModel(table, dbh);
        List<Map<String, Object>> returnList = model.find(2);

        EasyMock.verify(dbh);
        Assert.assertEquals(returnList.get(0).get("id").toString(), row2.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("domain").toString(), row2.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("redirect_domain").toString(), row2.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("status").toString(), row2.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("use_path").toString(), row2.getEntry(4).getValue().toString());
    }


    @Test
    public void deleteById() {
        MockTable table = new MockTable();

        Map<String, Object> map = new HashMap<>();
        map.put("domain", "test.com");
        map.put("redirect_domain", "test2.com");
        map.put("status", "301");
        map.put("use_path", "0");

        SQLiteDatabaseHelper dbh = EasyMock.createNiceMock(SQLiteDatabaseHelper.class);
        dbh.open();
        EasyMock.expectLastCall();
        EasyMock.expect(dbh.removeById(table, 2)).andReturn(true);
        dbh.close();
        EasyMock.expectLastCall();

        EasyMock.replay(dbh);

        RedirectsModel model = new RedirectsModel(table, dbh);
        Assert.assertTrue(model.delete(2));

        EasyMock.verify(dbh);
    }

    @Test
    public void updateById() {
        MockTable table = new MockTable();

        Map<String, Object> map = new HashMap<>();
        map.put("domain", "test2.com");
        map.put("redirect_domain", "test3.com");
        map.put("status", "404");
        map.put("use_path", 1);

        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("domain", "test2.com");
        mapParams.put("redirect_domain", "test3.com");
        mapParams.put("status", "404");
        mapParams.put("use_path", "1");

        SQLiteDatabaseHelper dbh = EasyMock.createNiceMock(SQLiteDatabaseHelper.class);
        dbh.open();
        EasyMock.expectLastCall();
        EasyMock.expect(dbh.updateById(table, 2, mapParams)).andReturn(row2);
        dbh.close();
        EasyMock.expectLastCall();

        EasyMock.replay(dbh);

        RedirectsModel model = new RedirectsModel(table, dbh);
        List<Map<String, Object>> returnList = model.update(2, map);

        EasyMock.verify(dbh);

        Assert.assertEquals(returnList.get(0).get("id").toString(), row2.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("domain").toString(), row2.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("redirect_domain").toString(), row2.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("status").toString(), row2.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("use_path").toString(), row2.getEntry(4).getValue().toString());
    }
}
