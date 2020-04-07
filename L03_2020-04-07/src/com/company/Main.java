package com.company;

import org.json.JSONArray;

import  java.io.*;

public class Main {

    public static void main(String[] args){
        WebPage wp = new WebPage();
        String temp = wp.GetDataFromWebPage("http://ux.up.krakow.pl/~pmazurek/java/read.php");

        JSON jObj = new JSON();
        jObj.GetDataFromJSON(temp);
        JSONArray tempArr = jObj.SetDateToJSON();

        wp.SendDateToWebPage(tempArr.toString(), "http://ux.up.krakow.pl/~pmazurek/java/add.php");
    }
}
