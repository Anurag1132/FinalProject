package com.example.shoponline.api_interface;


import com.example.shoponline.R_Models.CreateWishlist;
import com.example.shoponline.R_Models.ViewWishlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolder {


    @POST("addWishlist")
    Call<CreateWishlist> SendWishlist(@Body CreateWishlist user_Wishlist);

    @POST("viewWishlist")
    Call<List<CreateWishlist>> View_Wishlist(@Body ViewWishlist user_Wishlist_view);


}
