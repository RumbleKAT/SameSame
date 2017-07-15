package com.example.songmyeongjin.samesame;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SyncFailedException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    final int PICTURE_REQUEST_CODE = 100;
    ArrayList<Object> w = new ArrayList<Object>(); //w 주소 저장
    String callback = "";
    String celebrity;
    ImageView In;
    com.gc.materialdesign.views.Button sub;
    TextView matching;
    WebImage web = new WebImage();
    Handler handler = new Handler();
    Button nextPage;

    //닮은 꼴 연예인의 데이터를 전송받고 그 연예인의 기사를 api로 받아와서 리스트로 정렬

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childview);

        matching = (TextView) findViewById(R.id.matching);
        nextPage = (Button) findViewById(R.id.nextPage);
        nextPage.setVisibility(View.INVISIBLE);

        celebrity = "데이터가 존재하지 않습니다.";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }  //액션바에 데이터 전송 버튼 생성

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_button) {
            Toast.makeText(this, "닮은 꼴 연예인을 찾습니다!", Toast.LENGTH_SHORT).show();

            try {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
            }
            catch (Exception e){
                System.out.println(e);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void nextPage(View v){
        String str = "다음 페이지로 넘어감!";

        Intent intent = new Intent(getApplicationContext(), NewsPage.class);
        //intent.putExtra("celebrity", celebrity);
        System.out.println("다음 페이지로 넘어가기 전에");


        for(int i = 0; i<10;i++){
            System.out.println(w.get(i));
        }
        //intent.putExtra("url",w);
        startActivity(intent);

    }

    public void showImage() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                In = (ImageView) findViewById(R.id.childimage);
                try {
                    URL url = new URL(w.get(0).toString());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            In.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void showCelebrity() {
        matching.setText(celebrity);
        nextPage.setVisibility(View.VISIBLE);
    }

    public String getPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
        return path;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // In.setImageResource(0);

                final Uri uri = data.getData();
                if (uri != null) {
                    final String filepath = getPathFromUri(uri);

                    new Thread(new Runnable() {
                        public void run() {
                            FaceTrack facebook = new FaceTrack();
                            try {
                                callback = facebook.sendImage(filepath);
                                //이미지 호출
                                GetJson getJson = new GetJson();
                                ArrayList<String> arr = getJson.getData(callback); //검색 실시
                                System.out.println(arr.get(0));
                                celebrity = arr.get(0);
                                System.out.println("Text : " + celebrity);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable(){
                                            @Override
                                            public void run() {
                                                showCelebrity();
                                            }
                                        });
                                    }
                                }).start();

                                //web 검색
                                String Image = web.getWebSearchImage(arr.get(0));
                                System.out.println("----------------------");

                                w = getJson.getImage(Image);
//                                System.out.println(w.get(0));
                                showImage();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }


    }
}



