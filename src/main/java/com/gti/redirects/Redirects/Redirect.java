/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gti.redirects.Redirects;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author xach
 */
public class Redirect {
	private String domain;

	public String getType() {
		return type;
	}

	public String getRedirect_to() {
		return redirect_to;
	}

	public String getDomain() {
		return domain;
	}

	private String type;
	private String redirect_to;

	public Redirect(String domain, String type, String redirect_to) {
		this.domain = domain;
		this.type = type;
		this.redirect_to = redirect_to;
	}


	public JSONObject toJsonObject() {
		JSONObject redirect = new JSONObject();
		redirect.put("domain", domain);
		redirect.put("type", type);
		redirect.put("redirect_to", redirect_to);
		return redirect;
	}
	
	public Map<String, String> toMap() {
		Map map = new HashMap();
		map.put("domain", domain);
		map.put("type", type);
		map.put("redirect_to", redirect_to);
		return map;
	}	
}
