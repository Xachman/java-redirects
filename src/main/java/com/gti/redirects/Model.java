package com.gti.redirects;

import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/11/17.
 */

public interface Model {
   public List<Map<String, Object>> find();
   public List<Map<String, Object>> find(int id);
   public boolean save(Map<String, Object> data);
   public List<Map<String, Object>> update(int id, Map<String, Object> data);
   public boolean delete(int id);
}
