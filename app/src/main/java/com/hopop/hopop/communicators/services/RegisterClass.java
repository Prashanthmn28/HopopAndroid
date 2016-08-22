package com.hopop.hopop.communicators.services;

import com.hopop.hopop.destination.data.ForSeatAvailability;
import com.hopop.hopop.login.data.LoginUser;
import com.hopop.hopop.payment.data.BookIdInfo;
import com.hopop.hopop.payment.data.ForBookId;
import com.hopop.hopop.payment.data.WalletInfo;
import com.hopop.hopop.ply.data.SeatTimeInfo;
import com.hopop.hopop.registration.data.RegisterUser;
import com.hopop.hopop.response.Registerresponse;
import com.hopop.hopop.sidenavigation.feedback.data.FeedBackField;
import com.hopop.hopop.sidenavigation.feedback.data.FeedbackInfo;
import com.hopop.hopop.sidenavigation.mybooking.data.BookingHisInfo;
import com.hopop.hopop.database.ProfileDetail;
import com.hopop.hopop.sidenavigation.profile.data.ProfileDetailsInfo;
import com.hopop.hopop.sidenavigation.profile.data.ProfileUpdatedInfo;
import com.hopop.hopop.sidenavigation.suggestedroute.data.ForSuggestedRoute;
import com.hopop.hopop.sidenavigation.suggestedroute.data.SuggestedInfo;
import com.hopop.hopop.source.data.ForProfileHeader;
import com.hopop.hopop.source.data.HeaderProfile;
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

    @POST("wallet_info.php")
    Call<WalletInfo> forWallet(@Body LoginUser loginUser);

    @POST("suggested_routes.php")
    Call<SuggestedInfo> forRoute (@Body ForSuggestedRoute forSuggestR);

    @POST("booking_info.php")
    Call<BookIdInfo> forBookIdInfo(@Body ForBookId forBookId);

    @POST("profile_details.php")
    Call<ProfileDetailsInfo> forProfile(@Body LoginUser loginUser);

    @POST("profile_update.php")
    Call<ProfileUpdatedInfo> updateProfile(@Body ProfileDetail profileDetail);

    @POST("booking_history.php")
    Call<BookingHisInfo> bookingHis(@Body LoginUser loginUser);

    @POST("user_feedback.php")
    Call<FeedbackInfo> feedbackInfo(@Body FeedBackField feedBackField);

    @POST("profile_info.php")
    Call<HeaderProfile> headerProfile(@Body ForProfileHeader forProfileHeader);
}