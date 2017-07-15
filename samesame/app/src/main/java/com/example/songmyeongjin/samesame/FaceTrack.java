package com.example.songmyeongjin.samesame;

import android.content.ClipData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by songmyeongjin on 2017. 4. 28..
 */

public class FaceTrack extends ClientDATA {
    //얼굴인식을 위한 클래스
    StringBuffer reqStr = new StringBuffer();

    public String sendImage(String imgFile) throws IOException {

        String sendResult = "";

        try{
        String paramName = "image";
        File uploadFile = new File(imgFile);
        String apiURL = "https://openapi.naver.com/v1/vision/celebrity";
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);

        String boundary = "---"+ System.currentTimeMillis()+"---";
        con.setRequestProperty("Content-Type","multipart/form-data; boundary="+boundary);
        con.setRequestProperty("X-Naver-Client-Id", clientID);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
        OutputStream outputStream = con.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);
        String LINE_FEED = "\r\n";

        String filename = uploadFile.getName();
        writer.append("--"+boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + filename + "\"").append(LINE_FEED);
        writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(filename)).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
        BufferedReader br = null;
        int responseCode = con.getResponseCode();
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            System.out.println("error!!!!!!! responseCode= " + responseCode);
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
        String inputLine;
        if(br != null) {
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            sendResult = response.toString();
        } else {
            System.out.println("error !!!");
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    finally {
            return sendResult;
        }
}
}
