package com.hjh.likecafe;

public class ReviewVO {

    private int id;
    private int cafe_id;
    private String mem_id;
    private int star;
    private String content;
    private String image;

    public ReviewVO(int id, int cafe_id, String mem_id, int star, String content, String image) {
        this.id = id;
        this.cafe_id = cafe_id;
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

    public String getMem_id() {
        return mem_id;
    }

    public int getStar() {
        return star;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

}
