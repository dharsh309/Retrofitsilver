package com.example.india.retrofitsilver.Api_interface;

import com.example.india.retrofitsilver.Api_Response.LoginResponse;
import com.example.india.retrofitsilver.Api_Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {     //set interface


    @FormUrlEncoded
    @POST("controller.php")//file name in htdocs
    Call<RegisterResponse> RegisterResponse(                    //response class name
            @Field("register") String tag,
            @Field("name") String name,
            @Field("phoneno") String phoneno,
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("login_controller.php")//file name in htdocs
    Call<LoginResponse> LoginResponse(
            //response class name

            @Field("login") String tag,
            @Field("name") String name,
//            @Field("phoneno") String phoneno,
//            @Field("email") String email,
            @Field("password") String password);
}





