package com.example.unishop.pincode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPIs {

    @GET("/api/pincode/{PINCODE}")
    Call< WResponse > getPincode(@Path("PINCODE") String pincode);


}
