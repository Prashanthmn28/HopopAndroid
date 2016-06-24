package com.hopop.hopop.communicators.services;

import com.hopop.hopop.destination.data.ForSeatAvailability;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.ply.data.SeatTimeInfo;
import com.hopop.hopop.registration.data.RegisterUser;
import com.hopop.hopop.response.Registerresponse;
import com.hopop.hopop.source.data.SourceList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterClass {
    public static final String ACCEPT_JSON = "Accept: application/json";

    public static final String CONTENT_TYPE_JSON = "Content-Type: application/json";
    @POST("register_user.php")
    @Headers({ACCEPT_JSON, CONTENT_TYPE_JSON})
    Call<Registerresponse> groupListReg(@Body RegisterUser registerUser);
    @POST("user_details.php")
    Call<Registerresponse> groupListLogin(@Body LoginUser loginUser);

    @GET("from_route.php")
    Call<SourceList> groupListSrc();

    @POST("seat_time_info.php")
    Call<SeatTimeInfo> forSeatAvailiability(@Body ForSeatAvailability forSeats);

}