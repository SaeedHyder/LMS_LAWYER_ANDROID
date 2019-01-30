package com.ingic.lmslawyer.retrofit;


import com.ingic.lmslawyer.entities.BookmarkEnt;
import com.ingic.lmslawyer.entities.LawyerRateResponse;
import com.ingic.lmslawyer.entities.LawyerStepsEnt;
import com.ingic.lmslawyer.entities.LawyerType_CaseItem;
import com.ingic.lmslawyer.entities.NewCasesEnt;
import com.ingic.lmslawyer.entities.NewsFeedItem;
import com.ingic.lmslawyer.entities.NotificationEnt;
import com.ingic.lmslawyer.entities.Profile.ProfileServicesEnt;
import com.ingic.lmslawyer.entities.ResponseWrapper;
import com.ingic.lmslawyer.entities.Specialization_Experience_CategoryItem;
import com.ingic.lmslawyer.entities.User;
import com.ingic.lmslawyer.entities.event_entities.EventEnt;
import com.ingic.lmslawyer.entities.library_entities.Case;
import com.ingic.lmslawyer.entities.library_entities.LibraryEnt;
import com.ingic.lmslawyer.entities.my_cases_entities.MyCases;
import com.ingic.lmslawyer.entities.my_cases_entities.PendingOrActiveCaseDetails;
import com.ingic.lmslawyer.entities.newsfeed.AllCategoriesEnt;
import com.ingic.lmslawyer.entities.newsfeed.Categories;
import com.ingic.lmslawyer.entities.newsfeed.NewsfeedEnt;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebService {


    // ********************* my Services ********************************
    @FormUrlEncoded
    @POST("register")
    Call<ResponseWrapper<User>> register(
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("device_type") String device_type,
            @Field("device_token") String device_token,
            @Field("role_id") Double role_id
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseWrapper<User>> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_type") String device_type,
            @Field("device_token") String device_token,
            @Field("role_id") Double role_id
    )
            ;

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ResponseWrapper> forgotPassword(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("codeVerification")
    Call<ResponseWrapper> codeVerification(
            @Field("email") String email,
            @Field("verification_code") String verification_code
    );

    @FormUrlEncoded
    @POST("resetPassword")
    Call<ResponseWrapper> resetPassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation);


    @FormUrlEncoded
    @POST("updateDeviceToken")
    Call<WebResponse> updateDeviceToken(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type
    );

    @FormUrlEncoded
    @POST("pushOnOff")
    Call<ResponseWrapper<User>> pushOnOff(
            @Header("token") String token,
            @Field("status") String status
    );
    @FormUrlEncoded
    @POST("subscribeCategory")
    Call<ResponseWrapper<Case>> subscribeCategory(
            @Header("token") String token,
            @Field("category_id") Integer category_id
    );
    @FormUrlEncoded
    @POST("markFavourite")
    Call<ResponseWrapper> markFavourite(
            @Header("token") String token,
            @Field("newsfeed_id") Integer category_id
    );

    @FormUrlEncoded
    @POST("markUnFavourite")
    Call<ResponseWrapper> markUnFavourite(
            @Header("token") String token,
            @Field("newsfeed_id") Integer category_id
    );
    @FormUrlEncoded
    @POST("changePassword")
    Call<ResponseWrapper<User>> changePassword(
            @Header("token") String token,
            @Field("old_password") String old_password,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @FormUrlEncoded
    @POST("logout")
    Call<ResponseWrapper> logout(
            @Header("token") String token,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Field("old_password") String old_password
    );


    // *********************** Get Service *************************************

    /*@GET("getprofile")
    Call<WebResponse<User>> getProfile(
            @Query("user_id") int user_id);*/

   /* @GET("getnotifications")
    Call<WebResponse<ArrayList<NotificationListItem>>> getNotification(
            @Query("user_id") Integer user_id);*/

    /*@GET("getUnreadNotificationsCount")
    Call<WebResponse<NotificationCount>> getNotificationCount(
            @Query("user_id") int user_id);*/

    @GET("getnewsfeed")
    Call<WebResponse<ArrayList<NewsFeedItem>>> getNewsFeed(
            @Query("user_id") String user_id,
            @Query("category_ids") String category_ids
    );

    /*@GET("getnewsfeedDetail")
    Call<WebResponse<NewsFeedItem>> getNewsFeedDetail(
            @Query("user_id") int user_id,
            @Query("newsfeed_id") int newsfeed_id);*/

    /*@GET("getlawyerType")
    Call<WebResponse<ArrayList<LawyerType_CaseItem>>> getLawyerType(
            @Query("user_id") String user_id);*/

    @GET("getlawyerCase")
    Call<WebResponse<ArrayList<LawyerType_CaseItem>>> getLawyerCase(
            @Query("user_id") String user_id);


    /*@GET("getSpecialization")
    Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> getSpecialization(
            @Query("user_id") String user_id);*/

    @GET("getExperience")
    Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> getExperience(
            @Query("user_id") String user_id);

    @GET("getCategory")
    Call<WebResponse<ArrayList<Specialization_Experience_CategoryItem>>> getCategory(
            @Query("user_id") Integer user_id);

    /*************************** Lawyer Profile service ********************/

    @GET("getCaseRate")
    Call<ResponseWrapper<ArrayList<LawyerRateResponse>>> getLawyerRates(
            @Header("token") String token
    );


    @GET("getAffilation")
    Call<ResponseWrapper<ArrayList<LawyerRateResponse>>> getLawyerAffiliation(
            @Header("token") String token
    );

    @GET("getExperience")
    Call<ResponseWrapper<ArrayList<LawyerRateResponse>>> getLawyerExperience(
            @Header("token") String token
    );

    @GET("getLawerNewCase")
    Call<ResponseWrapper<NewCasesEnt>> getLawyerNewCases(
            @Header("token") String token
    );

    @GET("getEventUsers")
    Call<ResponseWrapper<ArrayList<User>>> getEventUsers(
            @Header("token") String token,
            @Query("event_id") int event_id
    );

    @FormUrlEncoded
    @POST("createEvent")
    Call<ResponseWrapper> createEvent(
            @Header("token") String token,
            @Field("case_id") String case_id,
            @Field("title") String title,
            @Field("date") String date,
            @Field("time") String time,
            @Field("member_ids") String member_ids,
            @Field("is_reminder") int is_reminder

    );

    @FormUrlEncoded
    @POST("updatedEvent")
    Call<ResponseWrapper> updateEvent(
            @Header("token") String token,
            @Field("event_id") int event_id,
            @Field("case_id") String case_id,
            @Field("title") String title,
            @Field("date") String date,
            @Field("time") String time,
            @Field("member_ids") String member_ids,
            @Field("is_reminder") int is_reminder

    );


    @FormUrlEncoded
    @POST("eventDelete")
    Call<ResponseWrapper> deleteEvent(
            @Header("token") String token,
            @Field("event_id") int event_id
    );

    @GET("getEvents")
    Call<ResponseWrapper<EventEnt>> getEvents(
            @Header("token") String token,
            @Query("year") int year,
            @Query("month") int month,
            @Query("case_id") String case_id
    );

    @GET("getSpecialization")
    Call<ResponseWrapper<ArrayList<ProfileServicesEnt>>> getSpecialization(
            @Header("token") String token
    );

    @GET("getLawyerSteps")
    Call<ResponseWrapper<LawyerStepsEnt>> getLawyerSteps(
            @Header("token") String token
    );

    @GET("getLawyerType")
    Call<ResponseWrapper<ArrayList<ProfileServicesEnt>>> getLawyerType(
            @Header("token") String token
    );

    @GET("getCaseTypes")
    Call<ResponseWrapper<ArrayList<ProfileServicesEnt>>> getCaseTypes(
            @Header("token") String token
    );

    @GET("getLanguage")
    Call<ResponseWrapper<ArrayList<ProfileServicesEnt>>> getLanguage(
            @Header("token") String token
    );

    @Multipart
    @POST("updateLawyerProfile")
    Call<ResponseWrapper<User>> updateLawyerProfile(
            @Header("token") String token,
            @Part("full_name") RequestBody full_name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("fees") RequestBody fees,
            @Part("bio") RequestBody bio,
            @Part("academics") RequestBody academics,
            @Part("affilation_id") RequestBody affilation_id,
            @Part("award") RequestBody award,
            @Part("experience_id") RequestBody experience_id,
            @Part MultipartBody.Part profile_image,
            @Part ArrayList<MultipartBody.Part> lawyer_resume,
            @Part("specialization_ids") RequestBody specialization_ids,
            @Part("law_ids") RequestBody law_ids,
            @Part("lawyertype_ids") RequestBody lawyertype_ids,
            @Part("case_ids") RequestBody case_ids,
            @Part("language_ids") RequestBody language_ids,
            @Part("location") RequestBody location,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude
    );


    @FormUrlEncoded
    @POST("getCaseLibrary")
    Call<ResponseWrapper<Case>> getCaseLibrary(
            @Header("token") String token,
            @Field("case_id") String case_id
    );

    @GET("getLawyerLibrary")
    Call<ResponseWrapper<LibraryEnt>> getUserLibrary(
            @Header("token") String token
    );


    @GET("getLawerNewCase")
    Call<ResponseWrapper<MyCases>> getLawyerNewCase(
            @Header("token") String token
    );

    @GET("getLawyerRecentCase")
    Call<ResponseWrapper<ArrayList<PendingOrActiveCaseDetails>>> getLawyerRecentCase(
            @Header("token") String token
    );


    @GET("getNotification")
    Call<ResponseWrapper<ArrayList<NotificationEnt>>> getNotification(
            @Header("token") String token
    );


    @FormUrlEncoded
    @POST("acceptCase")
    Call<ResponseWrapper> acceptOrRejectCase(
            @Header("token") String token,
            @Field("case_id") int case_id,
            @Field("status") int status
    );


    @GET("getUserProfile")
    Call<ResponseWrapper<User>> getUserProfile(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("updateToken")
    Call<ResponseWrapper> updateToken(
            @Header("token") String token,
            @Field("deviceToken") String deviceToken,
            @Field("deviceType") String deviceType);


    @GET("getCategories")
    Call<ResponseWrapper<ArrayList<Categories>>> getCategories(
            @Header("token") String token
    );

    @GET("getNewsfeed")
    Call<ResponseWrapper<ArrayList<NewsfeedEnt>>> getNewsfeed(
            @Header("token") String token,
            @Query("category_id") String category_id
    );


    @GET("getNewsfeed")
    Call<ResponseWrapper<ArrayList<NewsfeedEnt>>> getNewsfeedCategories(
            @Header("token") String token,
            @Query("sub_category_id") String sub_category_id
    );
    @GET("getCategories")
    Call<ResponseWrapper<ArrayList<AllCategoriesEnt>>> getAllCategories(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("suggestedCategory")
    Call<ResponseWrapper> suggestCategories(
            @Field("title") String title,
            @Header("token") String token

    );

    @GET("getUserProfile")
    Call<ResponseWrapper<User>> getProfile(
            @Header("token") String token
    );

    @GET("getMyMarkFeeds")
    Call<ResponseWrapper<ArrayList<BookmarkEnt>>> getMarkFeeds(
            @Header("token") String token
    );

    //Acknowledgement/reject case
    @FormUrlEncoded
    @POST("acknowledgeCase")
    Call<ResponseWrapper> acknowledgeCase(
            @Header("token") String token,
            @Field("id") int caseId,
            @Field("is_acknowledge") String is_acknowledge);

    /***********************************************************************/


}