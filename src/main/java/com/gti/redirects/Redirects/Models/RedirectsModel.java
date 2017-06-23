package com.gti.redirects.Redirects.Models;

import com.github.xachman.*;
import com.github.xachman.Where.Where;
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
            List<Map<String, Object>> output = convertRows(new ArrayList<>(Arrays.asList(dbh.getRowById(redirectsTable, id))));
        dbh.close();
        return output;
    }

    @Override
    public List<Map<String, Object>> save(Map<String, Object> data) {
        Map<String,String> newMap = convertObjectMapToString(data);

        dbh.open();
        Row row = dbh.insert(redirectsTable, newMap);
        dbh.close();


        List<Map<String, Object>> returnList = new ArrayList<>(convertRows(Arrays.asList(row)));
        return returnList;
    }

    @Override
    public List<Map<String, Object>> find(List<Map<String, String>> maps) {

        dbh.open();
        List<Row> row = dbh.searchTable(redirectsTable, maps);
        dbh.close();


        List<Map<String, Object>> returnList = new ArrayList<>(convertRows(row));
        return returnList;
    }

    @Override
    public List<Map<String, Object>> update(int id, Map<String, Object> data) {
        Map<String,String> newMap = convertObjectMapToString(data);

        dbh.open();
        Row row = dbh.updateById(redirectsTable, id, newMap);
        dbh.close();

        List<Map<String, Object>> returnList = new ArrayList<>(convertRows(Arrays.asList(row)));
        return returnList;
    }

    @Override
    public boolean delete(int id) {
        dbh.open();
        dbh.removeById(redirectsTable, id);
        dbh.close();
        return true;
    }

    private List<Map<String, Object>> convertRows(List<Row> rows) {

        List<Map<String, Object>> list = new ArrayList<>();
        for(Row row : rows) {
            if(row == null) continue;
            Map<String, Object> map = new HashMap<>();
            for(Entry entry: row.getEntries()) {
                map.put(entry.getColumn().name(), entry.getValue());
            }
            list.add(map);
        }

        return list;

    }

    private Map<String, String> convertObjectMapToString(Map<String, Object> data) {
        Map<String,String> newMap =new HashMap<String,String>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if(entry.getValue() instanceof String){
                newMap.put(entry.getKey(), (String) entry.getValue());
            }else if(entry.getValue() instanceof Integer) {
                newMap.put(entry.getKey(), Integer.toString((int) entry.getValue()));
            }
        }
        return newMap;
    }

    private List<Map<String, String>> convertListMap(List<Map<String, Object>> maps) {
        List<Map<String, String>> newMaps = new ArrayList<>();
        for(Map<String, Object> map: maps) {
           newMaps.add(convertObjectMapToString(map));
        }
        return newMaps;
    }
}
