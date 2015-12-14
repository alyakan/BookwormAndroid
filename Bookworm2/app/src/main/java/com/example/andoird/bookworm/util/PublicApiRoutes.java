package com.example.andoird.bookworm.util;

import com.example.andoird.bookworm.model.BookPage;
import com.example.andoird.bookworm.model.User;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by alyyakan on 12/13/15.
 */
public interface PublicApiRoutes {
    @POST("/users/new")
    Call<User> createUser(@Body User user);

    @POST("https://bookworm-alyakan.c9users.io/book_pages")
    Call<BookPage> createBookPage(@Body BookPage bookPage);

    @POST("/sessions")
    @FormUrlEncoded
    void login(@Field("session[email]") String email,
               @Field("session[password]") String password,
               Callback<User> callback);

    @GET("/books")
    void getProducts(Callback<List<BookPage>> callback);
}
