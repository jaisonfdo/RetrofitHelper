package droidmentor.helper.Retrofit;

import retrofit2.Response;

/**
 * Created by Jaison on 05/10/16.
 */

public interface APIListener
{
     void onSuccess(int from, Response response, Object res);
     void onFailure(int from, Throwable t);
     void onNetworkFailure(int from);
}
