package com.gti.redirects.Redirects;

import com.github.xachman.Table;
import com.gti.redirects.Model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xach on 5/13/17.
 */
public class RedirectsModel implements Model {
    private Table redirectsTable = new RedirectsTable();

    @Override
    public List<Map<String, String>> find() {
        return null;
    }

    @Override
    public List<Map<String, String>> find(String id) {
        return null;
    }

    @Override
    public boolean save(Map<String, String> data) {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
