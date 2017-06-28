package droidmentor.helper.Retrofit;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jaison on 05/10/16.
 */


public class APICall<T> {


    private Call call;
    private APIListener responseListener;
    private Activity activity;
    private Gson gson = new Gson();
    private ApiInterface apiService;
    private Context context;

    public static  boolean ShowNetworkError= true;
    public static String NETWORK_CONNECTION_ERROR = "Network connection failure.";
    public static String FAIL_ERROR = "Something went wrong! Please try again later.";
    public static String URL_EMPTY="Url is empty";
    public static String LISTENER_EMPTY="APIListener is null";
    public static String HEADER_EMPTY="Common header is empty";
    public static String GET_WITH_PARAMS="Get with parms is invalid,so param values are neglected";



    // Standard Object

    public APICall(Activity activity) {
        init(activity, ApiClient.getCommonHeaders(), null);
    }

    public APICall() {
        init(null, ApiClient.getCommonHeaders(), null);
    }

    public APICall(Map<String, String> customHeaders) {
        init(null, ApiClient.getCommonHeaders(), customHeaders);
    }

    // boolean flag decides the header occurance

    public APICall(Activity activity, boolean with_header) {
        if (with_header)
            init(activity, ApiClient.getCommonHeaders(), null);
        else
            init(activity, null, null);
    }

    // boolean flag decides the header occurance

    public APICall(Context context, Map<String, String> customHeaders) {
        this.context=context;
        init(null, ApiClient.getCommonHeaders(), customHeaders);
    }

    // you can add additional header

    public APICall(Activity activity, boolean with_header, Map<String, String> customHeaders) {

        if (with_header)
            init(activity, ApiClient.getCommonHeaders(), customHeaders);
        else
            init(activity,null, customHeaders);
    }

    // set default header

    public APICall(Activity activity, Map<String, String> defaultHeaders) {
        init(activity, defaultHeaders, null);

    }

    // set default and additional header

    public APICall(Activity activity, Map<String, String> defaultHeaders, Map<String, String> customHeaders) {
        init(activity, defaultHeaders, customHeaders);
    }

    // Standard Object

    public APICall(Activity activity,APIListener listener) {
        init(activity, ApiClient.getCommonHeaders(), null,listener);
    }

    // boolean flag decides the header occurance

    public APICall(Activity activity, boolean with_header,APIListener listener) {
        if (with_header)
            init(activity, ApiClient.getCommonHeaders(), null,listener);
        else
            init(activity, null, null);
    }

    // you can add additional header

    public APICall(Activity activity, boolean with_header, Map<String, String> customHeaders,APIListener listener) {

        if (with_header)
            init(activity, ApiClient.getCommonHeaders(), customHeaders,listener);
        else
            init(activity,null, customHeaders,listener);
    }

    // set default header

    public APICall(Activity activity, Map<String, String> defaultHeaders,APIListener listener) {
        init(activity, defaultHeaders, null,listener);

    }

    // set default and additional header

    public APICall(Activity activity, Map<String, String> defaultHeaders, Map<String, String> customHeaders,APIListener listener) {
        init(activity, defaultHeaders, customHeaders,listener);
    }


    public void init(Activity activity, Map<String, String> defaultHeaders, Map<String, String> customHeaders) {
        apiService = ApiClient.getClient(defaultHeaders, customHeaders);

        if(activity!=null)
        {
            this.activity = activity;
            if(activity!=null)
                this.context=activity;
            try
            {
                responseListener = (APIListener) activity;
            }catch (Exception e)
            {
                responseListener=null;
            }
        }
        else
            responseListener=null;


    }

    public void init(Activity activity, Map<String, String> defaultHeaders, Map<String, String> customHeaders,APIListener listener) {
        apiService = ApiClient.getClient(defaultHeaders, customHeaders);
        this.activity = activity;
        responseListener = listener;

        if(activity!=null)
            this.context=activity;
    }

    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                           final int from) {
        APIRequest(type, url,responseModel,body, params, from, false,"", responseListener);
    }

    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                           final int from, boolean isPDShow, String message) {
        APIRequest(type, url,responseModel,body, params, from, isPDShow,message, responseListener);
    }

    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                           APIListener responseListener) {
        APIRequest(type, url, responseModel, body, params, 1,false,"", responseListener);
    }

    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                           APIListener responseListener, boolean isPDShow, String message) {
        APIRequest(type, url, responseModel, body, params, 1,isPDShow,message, responseListener);
    }

    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                           final int from, String message) {
        APIRequest(type, url, responseModel, body, params, from, true,message, responseListener);
    }


    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                           String message, APIListener responseListener) {
        APIRequest(type, url,responseModel, body, params, 1, true, message, responseListener);
    }

    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                           boolean isPDShow, String message, APIListener responseListener) {
        APIRequest(type, url,responseModel, body, params, 1, isPDShow, message, responseListener);
    }


    public void APIRequest(Method type, String url, final Class<T> responseModel, RequestBody body, Map<String, String> params,
                            final int from, boolean is_PDshow, String message,
                            final APIListener responseListener) {
        call = null;

        String TAG = "APICall";

        if (TextUtils.isEmpty(url))
            Log.d(TAG, URL_EMPTY);
        else if(responseListener==null)
            Log.d(TAG, LISTENER_EMPTY);
        else if (Network_check.isNetworkAvailable(context)) {

            if(is_PDshow && activity!=null)
            {
                if (TextUtils.isEmpty(message))
                    ProgressDialogLoader.progressdialog_creation(activity, "Loading");
                else
                    ProgressDialogLoader.progressdialog_creation(activity, message);
            }

            if (params != null && !params.isEmpty() && body != null) {
                if (type == Method.GET) {
                    Log.d(TAG, GET_WITH_PARAMS);
                    call = apiService.getRequest(url, params);
                } else if (type == Method.POST)
                    call = apiService.postRequest(url, params, body);
                else if (type == Method.PUT)
                    call = apiService.putRequest(url, params, body);
                else if (type == Method.DELETE)
                    call = apiService.deleteRequest(url, params, body);
            } else if (params != null && !params.isEmpty()) {
                if (type == Method.GET)
                    call = apiService.getRequest(url, params);
                else if (type == Method.POST)
                    call = apiService.postRequest(url, params);
                else if (type == Method.PUT)
                    call = apiService.putRequest(url, params);
                else if (type == Method.DELETE)
                    call = apiService.deleteRequest(url, params);
            } else if (body != null) {
                if (type == Method.GET) {
                    Log.d(TAG, GET_WITH_PARAMS);
                    call = apiService.getRequest(url);
                } else if (type == Method.POST)
                    call = apiService.postRequest(url, body);
                else if (type == Method.PUT)
                    call = apiService.putRequest(url, body);
                else if (type == Method.DELETE)
                    call = apiService.deleteRequest(url, body);
            } else {
                if (type == Method.GET) {
                    call = apiService.getRequest(url);
                } else if (type == Method.POST)
                    call = apiService.postRequest(url);
                else if (type == Method.PUT)
                    call = apiService.putRequest(url);
                else if (type == Method.DELETE)
                    call = apiService.deleteRequest(url);
            }

            assert call != null;
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,Response<ResponseBody> response) {
                    try {

                        Log.d("Call URL",""+call.request().url().toString());
                        //Log.d("API Call", "Success");
                        String json = response.body().string();
                        //Log.d("API Call", "onSuccess: " + json);
                        T res = gson.fromJson(json, responseModel);
                        Response<ResponseBody> resp=response;
                        String json1 = resp.body().string();
                        //Log.d("API Call", "onSuccess: " + json1);

                        responseListener.onSuccess(from, resp, res);
                        ProgressDialogLoader.progressdialog_dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                        ProgressDialogLoader.progressdialog_dismiss();
                        responseListener.onFailure(from,new Throwable(e.getCause()));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Log error here since request failed
                    //Log.e("API Call","Failure"+t.toString());
                    responseListener.onFailure(from, t);
                    ProgressDialogLoader.progressdialog_dismiss();
                }
            });

        } else {
            responseListener.onNetworkFailure(from);
            Log.d(TAG,NETWORK_CONNECTION_ERROR);
        }


    }

    public void cancel()
    {
        call.cancel();
    }

    /**
     * Supported request methods.
     */

    public enum Method {
        GET, POST, PUT, DELETE;

    }
}
