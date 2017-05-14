package com.gti.redirectstests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by xach on 5/10/17.
 */
public class HttpRequest {
    private final String USER_AGENT = "Mozilla/5.0";
    private HttpURLConnection con;

    public void sendGet(String url) throws Exception {

        URL obj = new URL(url);
        con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);


    }

    public HttpURLConnection getConnection() {
        return con;
    }

    public String loadPage() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
