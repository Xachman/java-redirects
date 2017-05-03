package com.gti.redirects;

import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/2/17.
 */
public interface StorageI {
    List<Map<String, String>> getAll();
    boolean add(Map map);
    boolean delete(int Index);
}
