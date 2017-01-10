package com.example.jangminjae.wheather;

public class ListItem {

    private String[] mData;

    public ListItem(String[] data ){

        mData = data;
    }

    public ListItem(String imgurl, String txt1, String txt2, String txt3){

        mData = new String[4];
        mData[0] = imgurl;
        mData[1] = txt1;
        mData[2] = txt2;
        mData[3] = txt3;

    }

    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        return mData[index];
    }

    public void setData(String[] data){
        mData = data;
    }



}