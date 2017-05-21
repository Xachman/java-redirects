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
        row2 = new Row(entries);

        entries3 = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "3"),
                new Entry(new Column("text", "domain"), "test4.com"),
                new Entry(new Column("text", "redirect_domain"), "test4.com"),
                new Entry(new Column("text", "status"), "401"),
                new Entry(new Column("int", "use_path"), "0")
        ));
        row3 = new Row(entries);

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
        EasyMock.expect(dbh.insert(table, new ArrayList<String>(Arrays.asList(null, "test.com", "301", "test2.com", "0")))).andReturn(row);
        dbh.close();
        EasyMock.expectLastCall();

        EasyMock.replay(dbh);

        RedirectsModel model = new RedirectsModel(table, dbh);
        boolean save = model.save(map);
        System.out.println(save);



        EasyMock.verify(dbh);

        Assert.assertTrue(save);

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
        Assert.assertEquals(returnList.get(0).get("id"), row.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("domain"), row.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("redirect_domain"), row.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("status"), row.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("use_path"), row.getEntry(4).getValue().toString());

        Assert.assertEquals(returnList.get(1).get("id"), row2.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("domain"), row2.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("redirect_domain"), row2.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("status"), row2.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(1).get("use_path"), row2.getEntry(4).getValue().toString());

        Assert.assertEquals(returnList.get(2).get("id"), row3.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("domain"), row3.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("redirect_domain"), row3.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("status"), row3.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(2).get("use_path"), row3.getEntry(4).getValue().toString());
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
        Assert.assertEquals(returnList.get(0).get("id"), row2.getEntry(0).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("domain"), row2.getEntry(1).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("redirect_domain"), row2.getEntry(2).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("status"), row2.getEntry(3).getValue().toString());
        Assert.assertEquals(returnList.get(0).get("use_path"), row2.getEntry(4).getValue().toString());
    }
}
