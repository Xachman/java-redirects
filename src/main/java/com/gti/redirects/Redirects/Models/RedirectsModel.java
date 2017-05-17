package com.gti.redirects.Redirects.Models;

import com.github.xachman.Table;
import com.gti.redirects.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/13/17.
 */
public class RedirectsModel implements Model {
    private Table redirectsTable = new RedirectsTable();

    @Override
    public List<Map<String, Object>> find() {
        return null;
    }

    @Override
    public List<Map<String, Object>> find(int id) {
        return null;
    }

    @Override
    public boolean save(Map<String, Object> data) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
