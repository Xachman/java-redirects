package com.gti.redirects.Redirects.Models;

import com.github.xachman.*;
import com.gti.redirects.Model;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by xach on 5/13/17.
 */
public class RedirectsModel implements Model {
    private Table redirectsTable;
    private SQLiteDatabaseHelper dbh;

    public RedirectsModel( Table table, SQLiteDatabaseHelper dbh) {
        this.redirectsTable = table;
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
        dbh.open();
        System.out.println(id);
            List<Map<String, Object>> output = convertRows(new ArrayList<>(Arrays.asList(dbh.getRowById(redirectsTable, id))));
        dbh.close();
        return output;
    }

    @Override
    public boolean save(Map<String, Object> data) {
        Map<String,String> newMap =new HashMap<String,String>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if(entry.getValue() instanceof String){
                newMap.put(entry.getKey(), (String) entry.getValue());
            }
        }

        dbh.open();
        Row row = dbh.insert(redirectsTable, newMap);
        dbh.close();

        System.out.println(row);

        if(row != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> update(int id, Map<String, Object> data) {
        Map<String,String> newMap =new HashMap<String,String>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if(entry.getValue() instanceof String){
                newMap.put(entry.getKey(), (String) entry.getValue());
            }
        }

        dbh.open();
        Row row = dbh.updateById(redirectsTable, id, newMap);
        dbh.close();

        System.out.println(row);
        List<Map<String, Object>> returnList = new ArrayList<>(convertRows(Arrays.asList(row)));
        return returnList;
    }

    @Override
    public boolean delete(int id) {
        dbh.open();
        dbh.removeById(redirectsTable, 2);
        dbh.close();
        return true;
    }

    private List<Map<String, Object>> convertRows(List<Row> rows) {

        List<Map<String, Object>> list = new ArrayList<>();
        for(Row row : rows) {
            Map<String, Object> map = new HashMap<>();
            for(Entry entry: row.getEntries()) {
                System.out.println(entry.getValue().toString());
                map.put(entry.getColumn().name(), entry.getValue());
            }
            list.add(map);
        }

        return list;

    }
}
