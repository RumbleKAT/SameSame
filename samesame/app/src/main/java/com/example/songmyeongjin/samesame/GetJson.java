package com.example.songmyeongjin.samesame;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by songmyeongjin on 2017. 5. 1..
 */

public class GetJson {

    public ArrayList<Object> getImage(String request){
        ArrayList<Object> url = null;
        try{
            url = new ArrayList<Object>();

            JSONObject info = new JSONObject(request);
             String data = info.getString("items");//info 데이터를 string 값으로 저장

            JSONArray Image_detail = new JSONArray(data);
            System.out.println(Image_detail.toString());
            System.out.println(Image_detail.length());

            for(int i = 0; i < Image_detail.length();i++){
                JSONObject c = (JSONObject) Image_detail.get(i);//celebrity를 분리
                System.out.println(c.get("link"));

                url.add(c.get("link"));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            return url;
        }
    }
    //json 결과값을 바꿈
    public ArrayList<String>  getData(String request){
        ArrayList<String> value = null;
        try {
            value = new ArrayList<String>();
            ArrayList<Integer> confidence = new ArrayList<Integer>();

            JSONObject info = new JSONObject(request);
            JSONObject faceInfo = info.getJSONObject("info");//info 데이터를 string 값으로 저장


            System.out.println("---------");

            int facenum = faceInfo.getInt("faceCount");

            if(facenum != 0){
                String facedata = info.getString("faces");
                JSONArray face_detail = new JSONArray(facedata);
                System.out.println(face_detail.toString());

                System.out.println(face_detail.length());

                for(int i = 0; i < face_detail.length();i++){
                    JSONObject c = (JSONObject) face_detail.get(i);//celebrity를 분리
                    System.out.println(c.get("celebrity"));
                    JSONObject d = (JSONObject)c.get("celebrity");
                    System.out.println(d.get("value"));
                    System.out.println(d.get("confidence"));

                    value.add(d.get("value").toString());
                    confidence.add(Integer.parseInt(d.get("confidence").toString()));
                }

            }else{
                Log.i(TAG,"No data!");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return value;
        }
    }
}
