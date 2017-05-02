/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gti.redirects;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author xach
 */
public class Storage implements StorageI {
    private String file = "redirects.json";
    private File jsonFile;

    public Storage() {
        File redirectFile = new File(System.getProperty("user.dir") + "/" + file);
        try {
            if (redirectFile.createNewFile()) {
                JSONArray jsonArray = new JSONArray();
                jsonFile = redirectFile;
                writeJsonArray(jsonArray);
            } else {
                jsonFile = redirectFile;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Map<String, String>> redirects() {
        List<Map<String, String>> redirects = new ArrayList<Map<String, String>>();
        JSONArray jsonArray = null;
        try {
            jsonArray = parseFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("domain", object.get("domain").toString());
                map.put("type", object.get("type").toString());
                map.put("redirect_to", object.get("redirect_to").toString());

                redirects.add(map);
                // now do something with the Object
            }
        }
        return redirects;
    }

    @Override
    public boolean addRedirect(Redirect redirect) {
        try {
            System.out.println("Successfully Copied JSON Object to File...");
            JSONArray jsonArray = parseFile();
            JSONObject redirectJson = convertRedirectToJson(redirect);
            jsonArray.add(redirectJson);
            writeJsonArray(jsonArray);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private JSONArray parseFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        System.out.println(jsonFile.getPath());
        return (JSONArray) parser.parse(new FileReader(jsonFile.getPath()));
    }

    private JSONObject convertRedirectToJson(Redirect redirect) {
        JSONObject json = new JSONObject();

        json.put("domain", redirect.getDomain());
        json.put("type", redirect.getType());
        json.put("redirect_to", redirect.getRedirect_to());
        return json;
    }

    private boolean writeJsonArray(JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(jsonFile.getPath())) {
            file.write(jsonArray.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + jsonArray);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
