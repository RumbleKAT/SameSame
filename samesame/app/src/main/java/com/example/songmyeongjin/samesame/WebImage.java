package com.example.songmyeongjin.samesame;

import android.app.ProgressDialog;
import android.app.admin.SystemUpdatePolicy;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by songmyeongjin on 2017. 4. 27..
 */

public class WebImage extends ClientDATA {
    private ProgressDialog progressDialog;

    Bitmap bitmap;

    public String getWebSearchImage(String keyword)  {
        String text = "";
        String result = "";
        try {
            try {
                text = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String apiURL = "https://openapi.naver.com/v1/search/image.json?query=" + text + "&display=10&start=1&sort=sim";
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


    public Bitmap getImageBitmap(String url){
        Bitmap bm = null;
        try{
            URL urlco = new URL(url);
            URLConnection conn = urlco.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }


    public Bitmap getwebImage(final String webImageURL){
        bitmap = null;
        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(webImageURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return bitmap;

        }

    }
}
