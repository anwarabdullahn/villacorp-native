package anwarabdullahn.com.villacorp_apps.API;


import anwarabdullahn.com.villacorp_apps.Model.*;
import anwarabdullahn.com.villacorp_apps.Request.ChangeOffRequest;
import anwarabdullahn.com.villacorp_apps.Request.LoginRequest;
import anwarabdullahn.com.villacorp_apps.Request.PesanRequest;
import anwarabdullahn.com.villacorp_apps.Request.ReadRequest;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface VillaService {

    @POST("login/")
    Call<User> login(@Body LoginRequest body);

    @POST("logout/")
    Call<APIResponse> logout();

    @GET("info/")
    Call<Info> status();

    @GET("profile/")
    Call<Profile> profile();

    @POST("pesan/")
    Call<Pesans> pesan(@Body PesanRequest body);

    @POST("read/")
    Call<APIResponse> read(@Body ReadRequest body);

    @GET("offday/")
    Call<OffDay> offday();

    @GET("dop/in")
    Call<PublicHoliday> dopholiday();

    @GET("dop/out")
    Call<DOPMasuk> dopmasuk();

    @POST("dop/out")
    Call<APIResponse> postdop(@Body RequestBody body);

    @POST("dop/in")
    Call<APIResponse> dopin(@Body RequestBody body);

    @GET("dop/")
    Call<DOP> dop(@Query("page") String page);

    @POST("dop/")
    Call<MaxDOP> maxdop(@Body RequestBody body);

    @DELETE("dop/")
    Call<APIResponse> deletedop(@Query("id") String id);

    @GET("agenda/")
    Call<AgendaSlider> agendaSlider();

    @GET("jadwallibur/")
    Call<TukarLibur> jadwallibur(@Query("page") String page);

    @GET("changeoff/")
    Call<TukarLibur> changeoff(@Query("page") String page);

    @POST("changeoff/")
    Call<APIResponse> changeoff(@Body ChangeOffRequest body);

    @DELETE("changeoff/")
    Call<APIResponse> deletechangeoff(@Query("id") String id);

    @GET("changeinout/")
    Call<InOuts> changeinout(@Query("page") String page);

    @POST("changeinout/")
    Call<APIResponse> changeinout(@Body RequestBody body);

    @DELETE("changeinout/")
    Call<APIResponse> deletechangeinout(@Query("id") String id);

    @GET("leave_finger/")
    Call<LeaveFingers> leavefinger(@Query("page") String page);

}
