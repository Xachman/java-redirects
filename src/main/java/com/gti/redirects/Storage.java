/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gti.redirects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author xach
 */
public class Storage implements StorageI {
	private String file = "redirects.json";
	private File jsonFile;

	public Storage() {
		File redirectFile = new File(System.getProperty("user.dir")+"/"+file);
		try {
			redirectFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		jsonFile = redirectFile;

	}
	@Override
	public List redirects() {
		return null;
	}

	@Override
	public boolean addRedirect(Redirect redirect) {
        try {
            JSONArray jsonArray = parseFile();
            JSONObject redirectJson = convertRedirectToJson(redirect);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
	}

	private JSONArray parseFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(new FileReader(jsonFile.getPath()));
    }

    private JSONObject convertRedirectToJson(Redirect redirect) {
        JSONObject json = new JSONObject();

        json.put("domain", redirect.getDomain());
        json.put("type", redirect.getType());
        json.put("redirect_to", redirect.getRedirect_to());
        return json;
    }

}
