package com.hjh.likecafe;

import android.graphics.Bitmap;

public class ReviewVO {

    private int id;
    private int cafe_id;
    private String cafe_name;
    private String mem_id;
    private double star;
    private String content;
    private Bitmap image;

    public ReviewVO(int id, int cafe_id, String cafe_name, String mem_id, double star, String content, Bitmap image) {
        this.id = id;
        this.cafe_id = cafe_id;
        this.cafe_name = cafe_name;
        this.mem_id = mem_id;
        this.star = star;
        this.content = content;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getCafe_id() {
        return cafe_id;
    }

    public String getCafe_name() {
        return cafe_name;
    }

    public String getMem_id() {
        return mem_id;
    }

    public double getStar() {
        return star;
    }

    public String getContent() {
        return content;
    }

    public Bitmap getImage() {
        return image;
    }

}
