package com.gti.redirects;

import static spark.Spark.get;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        get("/create-redirect", (req, res) -> "Hello World!");
        get("/redirects", (req, res) -> "Hello World!");
        get("/", (req, res) -> "Hello World!");
    }
}
