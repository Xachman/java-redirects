package com.gti.redirects.Database;

import com.github.xachman.Column;
import com.github.xachman.Table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xach on 5/5/17.
 */
public class RedirectsTable extends Table {


    @Override
    public List<Column> columns() {

        return new ArrayList<Column>(Arrays.asList(
                new Column("integer", "id", true, true),
                new Column("text", "domain"),
                new Column("int", "status"),
                new Column("text","redirect_to"),
                new Column("int", "follow_path")
        ));
    }

    @Override
    public String tableName() {
        return "redirects";
    }
}
