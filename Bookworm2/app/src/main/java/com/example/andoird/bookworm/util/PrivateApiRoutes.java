package com.example.andoird.bookworm.util;

import retrofit.Callback;
import retrofit.Response;
import retrofit.http.PATCH;
import retrofit.http.Path;

/**
 * Created by alyyakan on 12/13/15.
 */
public interface PrivateApiRoutes {
    @PATCH("/products/{product_id}/buy")
    void patchProductBuy(@Path("product_id") long productId,
                         Callback<Response> callback);
}
