package com.gti.redirectstests;

import com.github.xachman.*;
import com.gti.redirects.Redirects.Models.RedirectsModel;
import com.gti.redirects.Redirects.Models.RedirectsTable;
import com.gti.redirectstests.Database.Mocks.MockTable;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by xach on 5/17/17.
 */
public class RedirectsModelTest {
    private String dbPath = System.getProperty("user.dir")+"/src/test/mock/database.db";
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

        List<Entry> entries = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "1"),
                new Entry(new Column("text", "domain"), "test.com"),
                new Entry(new Column("text", "redirect_domain"), "test2.com"),
                new Entry(new Column("text", "status"), "301"),
                new Entry(new Column("int", "use_path"), "0")
        ));
        Row row = new Row(entries);

        List<Entry> entries2 = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "2"),
                new Entry(new Column("text", "domain"), "test2.com"),
                new Entry(new Column("text", "redirect_domain"), "test3.com"),
                new Entry(new Column("text", "status"), "404"),
                new Entry(new Column("int", "use_path"), "1")
        ));
        Row row2 = new Row(entries);

        List<Entry> entries3 = new ArrayList<>(Arrays.asList(
                new Entry(new Column("integer","id"), "3"),
                new Entry(new Column("text", "domain"), "test4.com"),
                new Entry(new Column("text", "redirect_domain"), "test4.com"),
                new Entry(new Column("text", "status"), "401"),
                new Entry(new Column("int", "use_path"), "0")
        ));
        Row row3 = new Row(entries);

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
        Assert.assertEquals(returnList.get(2), row.getEntry(1).getValue().toString());

    }
}
