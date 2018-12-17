package anwarabdullahn.com.villacorp_apps.API;


import anwarabdullahn.com.villacorp_apps.Model.Info;
import anwarabdullahn.com.villacorp_apps.Model.Pesans;
import anwarabdullahn.com.villacorp_apps.Model.Profile;
import anwarabdullahn.com.villacorp_apps.Model.User;
import anwarabdullahn.com.villacorp_apps.Request.LoginRequest;
import anwarabdullahn.com.villacorp_apps.Request.PesanRequest;
import anwarabdullahn.com.villacorp_apps.Request.ReadRequest;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
}
