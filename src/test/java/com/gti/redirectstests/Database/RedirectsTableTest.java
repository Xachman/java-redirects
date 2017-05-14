package com.gti.redirectstests.Database;

import com.github.xachman.Column;
import com.gti.redirects.Database.RedirectsTable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xach on 5/10/17.
 */
public class RedirectsTableTest {
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
