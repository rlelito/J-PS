package com.company;

import org.json.JSONArray;

public class Main {

    public static void main(String[] args) {
        String pm_read = "http://ux.up.krakow.pl/~pmazurek/java/read.php";
        String up = "https://www.up.krakow.pl";

        WebPage wp = new WebPage();
        String temp = wp.GetDataFromWebPage(up);
        wp.GetLinksFromWebPage(temp);

//        JSON jObj = new JSON();
//        jObj.GetDataFromJSON(temp);
//        //JSONArray tempArr = jObj.SetDateToJSON();
//
//        //wp.SendDataToWebPage(tempArr.toString(), "http://ux.up.krakow.pl/~pmazurek/java/add.php");
//
//        System.out.println("Moje wpisy:");
//        wp.SendDataToWebPage(jObj.MyPost(138098).toString(), "http://ux.up.krakow.pl/~pmazurek/java/read.php");
    }
}
