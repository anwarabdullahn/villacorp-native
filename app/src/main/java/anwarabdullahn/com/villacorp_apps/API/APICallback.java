package anwarabdullahn.com.villacorp_apps.API;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by anwarabdullahn on 1/23/18.
 */
public abstract class APICallback<T> implements Callback<T> {
    @Deprecated
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
            Log.d("Response", response.body().toString());
        } else {
            try {
                APIError error = API.getErrorConverter().convert(response.errorBody());
                onError(error);
            } catch (IOException e) {
                onError(new APIError("Unexpected error"));
            }
        }
    }

    @Deprecated
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError(new APIError(t.getMessage() + ""));
    }

    protected abstract void onSuccess(T t);

    protected abstract void onError(APIError error);
}

