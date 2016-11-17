# RetrofitHelper

![ScreenShot](http://droidmentor.com/wp-content/uploads/2016/11/retrofit_execution-1080x989.jpg)

RetrofitHelper is an Open Source Android library that used to make HTTP request easily.

Here are some advantages of using RetrofitHelper over other libraries:

1. Network checking is not required.
2. No need to handle ProgressDialog while calling an API.
3. ApiInterface and ApiClinet are not required.
4. You can handle errors in a single place.
5. You can optimise your code.
6. Avoid code redundancy.

For using this networking library, you need to follow the below instructions.

<b>Step 1: </b> Add Dependency in build.gradle
<pre>  compile 'com.droidmentor:helper:1.0.1' </pre>
<b>Step 2: </b> Set Base URL and common headers if needed. Base URL field is mandatory.</br>
<b>Step 3: </b> This library checks your internet connection be default so if you want to toast message you can set the error message.
<pre>  ApiClient.setBaseUrl(BASE_URL);
  ApiClient.setNetworkErrorMessage("Network Error");
  ApiClient.showNetworkErrorMessage(false);</pre>
<b>Step 4: </b> APICall is the class do the magic. You can call the API using the following set of code.
<pre>  APICall networkCall;
  networkCall = new APICall(this);

  Map<String, String> queryParams = new HashMap<>();
  queryParams.put("user[email]", etUserEmail.getText().toString());
  queryParams.put("user[name]", etUserName.getText().toString());

  networkCall.APIRequest(APICall.Method.POST,"user",UserDetails.class,null,queryParams,1,"Adding");
</pre>

<b> Step 5: </b> APIRequest is the method simplifies your process and act wisely based on your parameters. This method can handle the following arguments.


* <b>type</b> : type of the request.
* <b>url</b> : endpoint
* <b>responseModel</b> : response model class.
* <b>body</b> :  request body.
* <b>params</b> : URL parameters.
* <b>from</b> : int value represent the source, because in a single page you can trigger multiple requests.
* <b>is_PDshow</b> : boolean flag shows the visibility of the ProgressDialog.
* <b>message</b> : Loader title message.
* <b>responseListene</b>r : It is an interface, it contains the callback methods (onSuccess, onFailure, onNetworkFailure).

In <b>AndroidManifest.xml</b><br>

Specify all the needed permissions. If you don't, permission requests fail silently. That's an Android thing.

