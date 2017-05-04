package com.gti.redirectstests;

import com.gti.redirects.Redirects.RedirectStorage;
import com.gti.redirects.StorageI;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xach on 5/3/17.
 */
public class RedirectStorageTest {

    @Test
    public void fileIsCreated() {
        String filePath = System.getProperty("user.dir") + "/src/test/mocks/tmp/test.json";
        StorageI storage = new RedirectStorage(filePath);
        File file = new File(filePath);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void fileCreatedIsJsonArray() {
        String filePath = System.getProperty("user.dir") + "/src/test/mocks/tmp/test.json";
        StorageI storage = new RedirectStorage(filePath);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(jsonArray, new JSONArray());
    }

    @Test
    public void listItemsReturnsListOfItems() {
        String filePath = System.getProperty("user.dir") + "/src/test/mocks/list-redirects.json";
        StorageI storage = new RedirectStorage(filePath);

        List<Map<String, String>> comList = new ArrayList<>();

        Map<String, String> item1 = new HashMap<>();
        item1.put("domain", "domain1.com");
        item1.put("status", "301");
        item1.put("redirect_to", "http://newdomain1.com");
        comList.add(item1);

        Map<String, String> item2 = new HashMap<>();
        item2.put("domain", "domain2.com");
        item2.put("status", "302");
        item2.put("redirect_to", "http://newdomain2.com");
        comList.add(item2);

        Map<String, String> item3 = new HashMap<>();
        item3.put("domain", "domain3.com");
        item3.put("status", "404");
        item3.put("redirect_to", "http://newdomain3.com");
        comList.add(item3);

       Assert.assertEquals(storage.getAll(), comList);

    }


    @After
    public void deleteFiles() {
        String directory = System.getProperty("user.dir") + "/src/test/mocks/tmp";
        try {
            FileUtils.cleanDirectory(new File(directory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
