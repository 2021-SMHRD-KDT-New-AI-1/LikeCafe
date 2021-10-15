package com.hjh.likecafe;

import java.util.Map;

public class CafeVO {

    private int id;
    private String name;
    private String image;
    private String address;
    private String business_hour;
    private String holiday;
    private String tel;
    private String sns;
    private String category;
    private Map<String, String> keyword; // 키워드 이름, 키워드 상세 내용을 저장하는 hashmap

    private int zzimCnt; // 해당 카페를 찜한 수
    private boolean zzimSel; // 해당 유저가 이 카페를 찜했는지 여부

    public CafeVO(int id, String name, String image, String address, String business_hour,
                  String holiday, String tel, String sns, String category,
                  Map<String, String> keyword, int zzimCnt, boolean zzimSel) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.business_hour = business_hour;
        this.holiday = holiday;
        this.tel = tel;
        this.sns = sns;
        this.category = category;
        this.keyword = keyword;
        this.zzimCnt = zzimCnt;
        this.zzimSel = zzimSel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getBusiness_hour() {
        return business_hour;
    }

    public String getHoliday() {
        return holiday;
    }

    public String getTel() {
        return tel;
    }

    public String getSns() {
        return sns;
    }

    public String getCategory() {
        return category;
    }

    public Map<String, String> getKeyword() {
        return keyword;
    }

    public int getZzimCnt() {
        return zzimCnt;
    }

    public boolean isZzimSel() {
        return zzimSel;
    }
}
