package droidmentor.helper.Retrofit;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jaison on 05/10/16.
 */


public class ApiClient
{

    private static String BASE_URL = "";

    private static Map<String, String> default_headers=new HashMap<>();


    // Without headers

    public static ApiInterface getClient()
    {
        return getClient(false,null);
    }


    // with/wo default headers

    public static ApiInterface getClient(boolean with_header)
    {
        return getClient(with_header,null);
    }

    // with  default headers


    public static ApiInterface getClient(final Map<String, String> defaultHeaders)
    {
        setCommonHeaders(defaultHeaders);
        return getClient(true,null);
    }

    // with  default header & custom header


    public static ApiInterface getClient(final Map<String, String> defaultHeaders,final Map<String, String> customHeaders)
    {
        setCommonHeaders(defaultHeaders);
        return getClient(true,customHeaders);
    }


    // Add custom headers with/wo default headers

    public static ApiInterface getClient(boolean with_header,final Map<String, String> customHeaders)
    {

        if(TextUtils.isEmpty(BASE_URL))
            BASE_URL="https://api.vookmark.co/";

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2,TimeUnit.MINUTES).build();

        Retrofit retrofit;

            Retrofit.Builder builder=new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create());

        String TAG = "APIClient";
        if(with_header&&default_headers!=null&&!default_headers.isEmpty())
            {
                Map<String, String> combined_headers=new HashMap<>();
                combined_headers.putAll(default_headers);
                if(customHeaders!=null)
                combined_headers.putAll(customHeaders);
                builder.client(get_HTTPClient(combined_headers));
            }
            else if(customHeaders!=null&&!customHeaders.isEmpty())
                builder.client(get_HTTPClient(customHeaders));
            else
                Log.d(TAG, APICall.HEADER_EMPTY);

            retrofit =builder.build();

        return retrofit.create(ApiInterface.class);
    }


    public static ApiInterface getApiClient(final Map<String, String> headers)
    {

        Retrofit retrofit;

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get_HTTPClient(headers))
                .addConverterFactory(GsonConverterFactory.create());

        retrofit =builder.build();

        return retrofit.create(ApiInterface.class);
    }

    // Get HTTPCLient with headers


    private static OkHttpClient get_HTTPClient(final Map<String, String> headers)
    {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers

                Request.Builder requestBuilder = original.newBuilder(); // <-- this is the important line

                for (Map.Entry<String, String> pairs : headers.entrySet()) {
                    if (pairs.getValue() != null) {
                        requestBuilder.header(pairs.getKey(), pairs.getValue());
                    }
                }

                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();

                return chain.proceed(request);

            }
        });

        return httpClient.build();

    }

    // Set Common Headers

    public static  void setCommonHeaders(final Map<String, String> commonHeaders)
    {
        if(commonHeaders != null&& !commonHeaders.isEmpty())
            default_headers=commonHeaders;

    }

    // Remove common headers

    public static void removeCommonHeaders()
    {
        if(default_headers!=null&&!default_headers.isEmpty())
            default_headers=new HashMap<>();
    }


    // Get common headers

     public static Map<String, String> getCommonHeaders()
    {
        return default_headers;
    }

    // Set Base URL

    public static void setBaseUrl(String baseUrl)
    {
        BASE_URL=baseUrl;
    }

    // Set Network Error Message

    public static void setNetworkErrorMessage(String errorMessage)
    {
        APICall.NETWORK_CONNECTION_ERROR=errorMessage;
    }

    // Set visibility of Network Error Message

    public static void showNetworkErrorMessage(boolean isShow)
    {
        APICall.ShowNetworkError=isShow;
    }
}
