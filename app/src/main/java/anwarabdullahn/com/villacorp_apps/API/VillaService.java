package anwarabdullahn.com.villacorp_apps.API;


import anwarabdullahn.com.villacorp_apps.Model.User;
import anwarabdullahn.com.villacorp_apps.Request.LoginRequest;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VillaService {

    @POST("login/")
    Call<User> login(@Body LoginRequest body);

    @POST("logout/")
    Call<APIResponse> logout();
}
