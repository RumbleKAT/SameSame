package com.example.songmyeongjin.samesame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by songmyeongjin on 2017. 6. 8..
 */

public class NewsSearch extends ClientDATA {

    public String getNewsData(String keyword)  {
        System.out.println("데이터를 불러옵니다.");
        String text = "";
        String result = "";
        try {
            try {
                text = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=10&start=1&sort=sim";
            try {
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", super.clientID );
                con.setRequestProperty("X-Naver-Client-Secret", super.clientSecret);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                result = response.toString();
            } catch(Exception e){
                System.out.println(e);
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return result; //json 타입으로 저장
        }
    }


}
