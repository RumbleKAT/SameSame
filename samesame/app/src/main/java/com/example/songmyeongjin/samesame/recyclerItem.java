package com.example.songmyeongjin.samesame;

/**
 * Created by songmyeongjin on 2017. 6. 7..
 */

public class recyclerItem {

    private String  title;
    private String description;

    public recyclerItem (String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
