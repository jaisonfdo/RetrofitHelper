package droidmentor.retrofitutil;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import droidmentor.helper.Retrofit.APICall;
import droidmentor.helper.Retrofit.APIListener;
import droidmentor.helper.Retrofit.ApiClient;
import retrofit2.Response;

import static droidmentor.retrofitutil.R.id.btn_add;
import static droidmentor.retrofitutil.R.id.btn_get;
import static droidmentor.retrofitutil.R.id.btn_update;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener, APIListener {

    String TAG = "Retrofit Example";
    APICall networkCall;

    public static final String BASE_URL = "http://api.droidmentor.com/";

    private EditText etUserName, etUserEmail;
    private TextInputLayout inputUserName, inputUserEmail;
    Button btnAdd, btnGet, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        inputUserName = (TextInputLayout) findViewById(R.id.input_layout_userName);
        inputUserEmail = (TextInputLayout) findViewById(R.id.input_layout_userEmail);

        etUserName = (EditText) findViewById(R.id.etUsername);
        etUserEmail = (EditText) findViewById(R.id.etUserEmail);

        btnAdd = (Button) findViewById(btn_add);
        btnGet = (Button) findViewById(btn_get);
        btnUpdate = (Button) findViewById(btn_update);

        btnAdd.setOnClickListener(this);
        btnGet.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        ApiClient.setBaseUrl(BASE_URL);
        ApiClient.setNetworkErrorMessage("Network Error");
        ApiClient.showNetworkErrorMessage(false);
        networkCall = new APICall(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_add:
                if (validateText(etUserName, inputUserName) && validateEmail(etUserEmail, inputUserEmail))
                        addUser();
                break;
            case btn_get:
                if (validateEmail(etUserEmail, inputUserEmail))
                        getUserDetails();
                break;
            case btn_update:
                if (validateText(etUserName, inputUserName) && validateEmail(etUserEmail, inputUserEmail))
                        updateUser();
                break;

        }
    }


    public void addUser() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("user[email]", etUserEmail.getText().toString());
        queryParams.put("user[name]", etUserName.getText().toString());

        networkCall.APIRequest(APICall.Method.POST,"user",UserDetails.class,null,queryParams,1,"Adding");
    }

    public void getUserDetails() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("user[email]", etUserEmail.getText().toString());

        networkCall.APIRequest(APICall.Method.GET,"user",UserDetails.class,null,queryParams,2,"Loading");
    }

    public void updateUser() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("user[email]", etUserEmail.getText().toString());
        queryParams.put("user[name]", etUserName.getText().toString());

        networkCall.APIRequest(APICall.Method.PUT,"user",UserDetails.class,null,queryParams,3,"Updating");
    }


    @Override
    public void onSuccess(int from, Response response, Object res) {
        switch (from) {
            case 1: {
                UserDetails userDetails= (UserDetails) res;

                if (userDetails.getDetails() != null) {

                    Log.d(TAG, "User ID: " + userDetails.getDetails().getId());
                    Toast.makeText(getApplicationContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                    etUserEmail.setText("");
                    etUserName.setText("");
                    inputUserEmail.setErrorEnabled(false);
                    inputUserName.setErrorEnabled(false);
                    requestFocus(etUserEmail);
                } else {
                    Log.d(TAG, "Something missing");
                }
            }
            break;
            case 2: {
                UserDetails userDetails= (UserDetails) res;

                if (userDetails.getDetails() != null) {
                    Log.d(TAG, "User ID: " + userDetails.getDetails().getId());
                    etUserName.setText(userDetails.getDetails().getName());
                } else {
                    Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "User details does not exist");
                }
            }
            break;
            case 3: {
                UserDetails userDetails= (UserDetails) res;
                if (userDetails.getDetails() != null) {
                    Log.d(TAG, "User ID: " + userDetails.getDetails().getId());
                    Toast.makeText(getApplicationContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                    requestFocus(etUserEmail);
                    Log.d(TAG, "Something missing");
                }
            }
            break;
        }
    }

    @Override
    public void onFailure(int from, Throwable t) {
        Log.e(TAG, t.toString());
    }

    @Override
    public void onNetworkFailure(int from) {

    }

    private boolean validateText(EditText etText, TextInputLayout inputlayout) {
        if (etText.getText().toString().trim().isEmpty()) {
            inputlayout.setError("Enter valid text");
            requestFocus(etText);
            return false;
        } else {
            inputlayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail(EditText etText, TextInputLayout inputlayout) {
        String email = etText.getText().toString().trim();

        if (!isValidEmail(email)) {
            inputlayout.setError("Enter valid Mail ID");
            requestFocus(etText);
            return false;
        } else {
            inputlayout.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
