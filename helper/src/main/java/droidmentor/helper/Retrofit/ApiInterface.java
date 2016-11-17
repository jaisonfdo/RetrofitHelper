package droidmentor.helper.Retrofit;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Jaison on 26/06/16.
 */
public interface ApiInterface<T>
{
    // GET request

    @GET
    Call<ResponseBody> getRequest(@Url String url);

    @GET
    Call<ResponseBody> getRequest(@Url String url, @QueryMap Map<String, String> params);


    // POST request


    @POST
    Call<ResponseBody> postRequest(@Url String url);

    @POST
    Call<ResponseBody> postRequest(@Url String url, @Body RequestBody post);

    @POST
    Call<ResponseBody> postRequest(@Url String url, @QueryMap Map<String, String> params);

    @POST
    Call<ResponseBody> postRequest(@Url String url, @QueryMap Map<String, String> params, @Body RequestBody post);


    // PUT request


    @PUT
    Call<ResponseBody> putRequest(@Url String url);

    @PUT
    Call<ResponseBody> putRequest(@Url String url, @Body RequestBody post);

    @PUT
    Call<ResponseBody> putRequest(@Url String url, @QueryMap Map<String, String> params);

    @PUT
    Call<ResponseBody> putRequest(@Url String url, @QueryMap Map<String, String> params, @Body RequestBody post);


    // DELETE request


    @DELETE
    Call<ResponseBody> deleteRequest(@Url String url);

    @DELETE
    Call<ResponseBody> deleteRequest(@Url String url, @Body RequestBody post);

    @DELETE
    Call<ResponseBody> deleteRequest(@Url String url, @QueryMap Map<String, String> params);

    @DELETE
    Call<ResponseBody> deleteRequest(@Url String url, @QueryMap Map<String, String> params, @Body RequestBody post);


}
