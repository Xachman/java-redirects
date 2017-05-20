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
        EasyMock.expect(dbh.insert(EasyMock.anyObject(Table.class), new ArrayList<String>(Arrays.asList(null, "test.com", "301", "test2.com", "0")))).andReturn(row);
        dbh.close();
        EasyMock.expectLastCall();

        EasyMock.replay(dbh);

        RedirectsModel model = new RedirectsModel(dbh);
        boolean save = model.save(map);
        System.out.println(save);



        EasyMock.verify(dbh);

        Assert.assertTrue(save);

    }
}
