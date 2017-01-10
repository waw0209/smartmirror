package com.example.jangminjae.wheather;

public class ListItem1 {

    private String[] mData;

    public ListItem1(String[] data ){

        mData = data;
    }

    public ListItem1(String imgurl, String txt1){

        mData = new String[2];
        mData[0] = imgurl;
        mData[1] = txt1;

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