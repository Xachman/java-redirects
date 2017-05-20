package com.gti.redirectstests.Database.Mocks;

import com.github.xachman.Column;
import com.github.xachman.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xach on 5/20/17.
 */
public class MockTable extends Table {
    @Override
    public List<Column> columns() {
        List<Column> columns = new ArrayList<>(Arrays.asList(
                new Column("integer", "id", true, true),
                new Column("text", "domain"),
                new Column("text", "status"),
                new Column("text", "redirect_domain"),
                new Column("int", "use_path")
        ));
        return columns;
    }

    @Override
    public String tableName() {
        return "redirects";
    }

    public Column findColumnNamed(String columnName) {
        for(Column column: columns()) {
            if(column.name().equals(columnName)) {
                return column;
            }
        }

        return null;
    }
}
