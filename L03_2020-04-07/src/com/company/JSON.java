package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.Set;

public class JSON {

    public void GetDataFromJSON(String dataJSON) {
        JSONObject obj = new JSONObject(dataJSON);
        for (String s: obj.keySet()) {
            System.out.println(s);
        }
        JSONArray objArr = obj.optJSONArray("post");
        for (int i = 0; i < objArr.length(); i++) {
            JSONObject tempObj = objArr.optJSONObject(i);
            System.out.println(tempObj.optString("nick"));
            System.out.println(tempObj.optString("data", "zly klucz"));
        }
    }

    public JSONArray SetDateToJSON() {
        int index = 138000; // twoj numer indeksu
        Scanner input = new Scanner(System.in);
        JSONObject obj = new JSONObject();
        obj.put("Indeks", index);
        System.out.println("Nick: ");
        String nick = input.nextLine();
        System.out.println("tresc postu: ");
        String post = input.nextLine();
        obj.put("Nick", nick);
        obj.put("Text", post);

        JSONArray objArr = new JSONArray();
        objArr.put(obj);
        return objArr;
    }
}
