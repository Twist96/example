package com.gasco.services.gescoServices;

import com.gasco.Models.ApiErrorResponse;
import com.gasco.Models.ApiListResponse;
import com.gasco.Models.DispenseLog;
import com.gasco.Models.DispenseLog2;
import com.gasco.Models.DispenseLogUpdate;
import com.gasco.Models.Skid;
import com.gasco.services.API;
import com.gasco.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DispenseLogService {

    private Gson gson;

    public DispenseLogService(){
        gson = new Gson();
    }

    public void GetDispenseLogs(int pageSize, int pageNumber, Date date, boolean assigned, boolean expand, final DispenseLogService.LogsCallBack callBack){
        String url = String.format(URLs.DISPENSE_LOGS_LIST, true, "SKID_FILL_SOP"); //getDate(date)
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", API.GetAuthorizationValue())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onFail(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String t = API.GetAuthorizationValue();
                String result = response.body().string();
                Gson gson = new Gson();
                if (response.code() >= 200 && response.code() <= 299){
                    Type type = new TypeToken<ApiListResponse<DispenseLog2>>(){}.getType();
                    ApiListResponse<DispenseLog2> dispenseLog = gson.fromJson(result, type);
                    callBack.onSuccess(dispenseLog.getItems());
                }else{
                    callBack.onFail(response.message());
                }
            }
        });
    }

    public void GetDispenseLog(String id, final DispenseLogService.LogCallBack callBack){
        String url = String.format(URLs.DISPENSE_LOG, id, true);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", API.GetAuthorizationValue())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onFail(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                DispenseLog dispenseLog = gson.fromJson(result, DispenseLog.class);
                callBack.onSuccess(dispenseLog);
            }
        });
    }

    public void GetDispenseLogWithUrl(String url, final DispenseLogService.LogCallBack callBack){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", API.GetAuthorizationValue())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callBack.onFail(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                DispenseLog dispenseLog = gson.fromJson(result, DispenseLog.class);
                callBack.onSuccess(dispenseLog);
            }
        });
    }


    public interface LogsCallBack{
        void onSuccess(List<DispenseLog2> skids);
        void onFail(String errorMessage);
    }

    public interface LogCallBack{
        void onSuccess(DispenseLog dispenseLog);
        void onFail(String errorMessage);
    }

    private String getDate(Date date){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        //Date today = Calendar.getInstance().getTime();
        return df.format(date);
    }
}
