package com.gti.redirects.Redirects.Models;

import com.github.xachman.Column;
import com.github.xachman.Row;
import com.github.xachman.SQLiteDatabaseHelper;
import com.github.xachman.Table;
import com.gti.redirects.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/13/17.
 */
public class RedirectsModel implements Model {
    private Table redirectsTable = new RedirectsTable();
    private SQLiteDatabaseHelper dbh;

    public RedirectsModel( SQLiteDatabaseHelper dbh) {
        this.dbh = dbh;
        this.dbh.createTable(redirectsTable);
        this.dbh.close();
    }

    @Override
    public List<Map<String, Object>> find() {
        dbh.open();
        List<Map<String, Object>> output = convertRows(dbh.getRows(redirectsTable));
        dbh.close();
        return output;
    }

    @Override
    public List<Map<String, Object>> find(int id) {
        return null;
    }

    @Override
    public boolean save(Map<String, Object> data) {

        ArrayList<String> values = new ArrayList<>();

        for(Column column: redirectsTable.columns()) {
            try {
                values.add(data.get(column.name()).toString());
            } catch (NullPointerException e) {
                values.add(null);
            }
        }

        System.out.println(values);

        dbh.open();
        Row row = dbh.insert(redirectsTable, values);
        dbh.close();

        System.out.println(row);

        if(row != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    private List<Map<String, Object>> convertRows(List<Row> rows) {

        List<Map<String, Object>> list = new ArrayList<>();
        int index = 0;
        for(Row row : rows) {
           Map<String, Object> map = new HashMap<>();
           map.put(row.getEntry(index).getColumn().name(), row.getEntry(index).getValue());
           index++;
        }

        return list;

    }
}
