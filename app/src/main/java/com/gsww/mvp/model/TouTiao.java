package com.gsww.mvp.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @package: com.gsww.mvp.ui.main
 * @author: liufx
 * @date: 2018/12/14 9:06 AM
 * Copyright © 2018 中国电信甘肃万维公司. All rights reserved.
 * @description: 简要描述
 */
public class TouTiao implements Serializable {


    /**
     * uniquekey : 051289c807a37021384e5c6a7100b52c
     * title : 厦门西先生西服定制——不甘平庸，所以注定不凡
     * date : 2018-08-21 14:43
     * author_name : 福建西先生
     * thumbnail_pic_s : http://09imgmini.eastday.com/mobile/20180821/20180821144345_6bdc300bd6f151daa9e0f6325a6b361c_2_mwpm_03200403.jpg
     * thumbnail_pic_s02 : http://09imgmini.eastday.com/mobile/20180821/20180821144345_6bdc300bd6f151daa9e0f6325a6b361c_3_mwpm_03200403.jpg
     * thumbnail_pic_s03 : http://09imgmini.eastday.com/mobile/20180821/20180821144345_6bdc300bd6f151daa9e0f6325a6b361c_1_mwpm_03200403.jpg
     * url : http://mini.eastday.com/mobile/180821144345643.html
     * category : 头条
     */

    private String uniquekey;
    private String title;
    private String date;
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("thumbnail_pic_s")
    private String thumbnailPicS;
    @SerializedName("thumbnail_pic_s02")
    private String thumbnailPicS02;
    @SerializedName("thumbnail_pic_s03")
    private String thumbnailPicS03;
    private String url;
    private String category;

    public static TouTiao objectFromData(String str) {

        return new Gson().fromJson(str,TouTiao.class);
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getThumbnailPicS() {
        return thumbnailPicS;
    }

    public void setThumbnailPicS(String thumbnailPicS) {
        this.thumbnailPicS = thumbnailPicS;
    }

    public String getThumbnailPicS02() {
        return thumbnailPicS02;
    }

    public void setThumbnailPicS02(String thumbnailPicS02) {
        this.thumbnailPicS02 = thumbnailPicS02;
    }

    public String getThumbnailPicS03() {
        return thumbnailPicS03;
    }

    public void setThumbnailPicS03(String thumbnailPicS03) {
        this.thumbnailPicS03 = thumbnailPicS03;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
