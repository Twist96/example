package com.gasco.services.gescoServices;

import com.gasco.Enums.QuestionType;
import com.gasco.Models.ApiErrorResponse;
import com.gasco.Models.ApiListResponse;
import com.gasco.Models.DispenseLog;
import com.gasco.Models.DispenseLogUpdate;
import com.gasco.Models.Equipment;
import com.gasco.Models.Question;
import com.gasco.Models.Skid;
import com.gasco.services.API;
import com.gasco.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SkidProcedureService {

    Gson gson;

    public SkidProcedureService(){
        gson = new Gson();
    }

    private Question selectSkid = new Question("Select Skid", QuestionType.dropdown);
    private Question compressorQuestion = new Question("Select Dispenser and Compressor", QuestionType.selectCompressor);

    public void GetQuestions(final QuestionsCallback callback){
        String url = String.format(URLs.LOGS_CHECK_LIST, true, "SKID_FILL_SOP");
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
                callback.onError(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                if (response.code() >= 200 && response.code() <= 299){
                    Type type = new TypeToken<ApiListResponse<Question>>(){}.getType();
                    ApiListResponse<Question> res = gson.fromJson(result, type);
                    res.getItems().add(0, selectSkid);
                    //res.getItems().add(1, compressorQuestion);
                    res.getItems().get(1).setQuestionType(QuestionType.selectCompressor);
                    callback.onSuccess(res.getItems());
                }
            }
        });
    }

    public void GetDemoQuestions(QuestionsCallback callBack){

        ArrayList<Question> questions = new ArrayList<>(
                Arrays.asList(
                        new Question("Select Skid", QuestionType.dropdown),
                        new Question("Select Dispenser and Compressor", QuestionType.dropdown),
                        new Question("Carry out a safety check (using safety checklist) on the truck and skid to confirm compliance to set safety standards.", QuestionType.checkbox),
                        new Question("Marshal the skid to loading bay, if safety standards are met.", QuestionType.checkbox),
                        new Question("Activate trailer brake system", QuestionType.checkbox),
                        new Question("A Connect ground cable to copper band on trailer pipework", QuestionType.checkbox),
                        new Question("Open drain valve to remove any pressure on male receptacle – close drain valve by hand –not with a tool", QuestionType.checkbox),
                        new Question("Clean male receptacle and lightly lubricate", QuestionType.checkbox),
                        new Question("Inspect hose for cuts, abrasions and blisters. Replace if defective.", QuestionType.checkbox),
                        new Question("Record residual pressure", QuestionType.text),
                        new Question("Record temperature on gauges", QuestionType.text)
                )
        );

        callBack.onSuccess(questions);
    }

    public void GetSkids(final SkidCallback callback){
        String url = String.format(URLs.SKIDS, true);
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
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                if (response.code() >= 200 && response.code() <= 299){
                    Type type = new TypeToken<ApiListResponse<Skid>>(){}.getType();
                    ApiListResponse<Skid> res = gson.fromJson(result, type);
                    callback.onSuccess(res.getItems());
                }
            }
        });
    }

    public void SelectSkid(String skidId, String skidLogTypeId, List<Equipment> equipments, final SelectSkidCallback callback){

        JsonObject jsonPayload = new JsonObject();
        Skid skid = new Skid();
        skid.setId(skidId);
        JsonElement  skidElement = gson.toJsonTree(skid);
        jsonPayload.add("skid", skidElement);
        jsonPayload.addProperty("skidLogTypeId", "SKID_FILL_SOP");
        JsonElement equipmentElement = gson.toJsonTree(equipments);
        jsonPayload.add("equipment", equipmentElement);
        String lm = jsonPayload.toString();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonPayload.toString());
        Request request = new Request.Builder()
                .url(URLs.FILL_LOGS)
                .method("POST", body)
                .addHeader("Authorization", API.GetAuthorizationValue())
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                if (response.code() >= 200 && response.code() <= 299) {
                    DispenseLog dispenseLog = gson.fromJson(result, DispenseLog.class);
                    callback.onSuccess(dispenseLog);
                    return;
                }
                callback.onFailure(String.valueOf(response.code()));
            }
        });
    }

    public void UpdateLog(String logId, DispenseLogUpdate dispenseLogUpdate, final SkidProcedureService.UpdateLogCallback callback){
        String url = String.format(URLs.DISPENSE_LOG_CHECKS, logId);
        String vv = gson.toJson(dispenseLogUpdate);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, gson.toJson(dispenseLogUpdate)); // "{\"readingTypeId\":\"RES_PRESSURE\",\"readValue\":\"250\"}");
        final Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Authorization", API.GetAuthorizationValue())
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                if (response.code() >= 200 && response.code() <= 299){
                    DispenseLog dispenseLog = gson.fromJson(result, DispenseLog.class);
                    callback.onSuccess(dispenseLog);
                }else {
                    ApiErrorResponse errorResponse = gson.fromJson(result, ApiErrorResponse.class);
                    callback.onFailure(errorResponse.getMessage());
                }
            }
        });
    }


    public interface QuestionsCallback {
        void onSuccess(List<Question> questions);
        void onError(String errorMessage);
    }

    public interface SkidCallback {
        void onSuccess(List<Skid> skids);
        void onFailure(String errorMessage);
    }

    public interface SelectSkidCallback{
        void onSuccess(DispenseLog dispenseLog);
        void onFailure(String errorMessage);
    }

    public interface CheckProcessCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface UpdateLogCallback {
        void onSuccess(DispenseLog dispenseLog);
        void onFailure(String errorMessage);
    }
}
