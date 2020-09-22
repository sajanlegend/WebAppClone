package edu.lwtech.finalproject;

public class Product {

    private String mImageUrl;
    private String mProductName;
    private float mUser_id;

    public  Product(String imageUrl, String productName,int user_id)
    {
        mImageUrl = imageUrl;
        mProductName = productName;
        mUser_id = user_id;

    }

    public String getmImageUrl()
    {
        return mImageUrl;
    }

    public String getmProductName()
    {
        return mProductName;

    }

    public float getUser()
    {
        return mUser_id;
    }


}
