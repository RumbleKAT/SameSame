package com.example.songmyeongjin.samesame;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songmyeongjin on 2017. 6. 6..
 */

public class NewsPage extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<recyclerItem> listitems;
    private NewsSearch newsitem;
    private String Celebirity = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Celebirity = getData();
        this.setTitle(Celebirity);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();
        //getImages(); // 이미지 출력


        for(int i=0;i<10;i++){
            listitems.add(new recyclerItem( Celebirity + (i+1),"Welcome, this is description "+(i+1)));

        }   //앞에 칸이 제목 뒤에 칸이 내


        adapter = new MyAdapter(listitems, this);
        recyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.back) {
            Toast.makeText(this, "원래 페이지로 돌아갑니다!", Toast.LENGTH_SHORT).show();
            backHome(); //처음 페이지로 돌아감
        }

        return super.onOptionsItemSelected(item);
    }

    public String getData(){
        Intent intent;
        intent = new Intent();
        String celebrity = intent.getExtras().getString("celebrity");

        ArrayList<String> arr = intent.getExtras().getStringArrayList("url");

        for(int i=0;i<arr.size();i++){
            System.out.println(arr.get(i));
        }

        return celebrity;
    }

    public void backHome(){
        Toast.makeText(this, "처음 페이지로 돌아갑니다.!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
