package com.gti.redirects;

import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/11/17.
 */

public interface Model {
   public List<Map<String, String>> find();
   public List<Map<String, String>> find(int id);
   public boolean save(Map<String, String> data);
   public boolean delete(int id);
}
