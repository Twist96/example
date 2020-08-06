package com.gasco.services.gescoServices;

import com.gasco.Models.ApiErrorResponse;
import com.gasco.Models.User;
import com.gasco.utils.URLs;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserService {

    public void LoginUser(String username, String password, final UserService.UserCallBack callBack){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("loginId", username);
            jsonBody.put("password", password);
            RequestBody body = RequestBody.create(mediaType, jsonBody.toString());
            Request request = new Request.Builder()
                    .url(URLs.LOGIN)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    callBack.onFail(e.getLocalizedMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String authorization = response.header("authorization");
                    String result = response.body().string();
                    Gson gson = new Gson();

                    if (response.code() != 200){
                        ApiErrorResponse error = gson.fromJson(result, ApiErrorResponse.class);
                        callBack.onFail(error.getMessage());
                        return;
                    }

                    User user = gson.fromJson(result, User.class);
                    callBack.onSuccess(user, authorization);
                }
            });
        }catch (Exception ex){
            callBack.onFail(ex.getLocalizedMessage());
        }
    }



    public interface UserCallBack{
        void onSuccess(User user, String authorization);
        void onFail(String errorMessage);
    }
}
