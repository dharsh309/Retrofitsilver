package com.example.india.retrofitsilver;

import android.content.Intent;
import android.service.autofill.Validator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.india.retrofitsilver.Api_Manager.ApiClient;
import com.example.india.retrofitsilver.Api_Response.LoginResponse;
import com.example.india.retrofitsilver.Api_interface.ApiInterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {
    /** ButterKnife Code **/
    @NotEmpty
    @BindView(R.id.Userid)
    EditText Userid;
    @Password
    @BindView(R.id.UserPass)
    EditText UserPass;
    @BindView(R.id.Login)
    Button Login;
    @BindView(R.id.UserRegister)
            Button UserRegister;
    /** ButterKnife Code **/
com.mobsandgeeks.saripaar.Validator validator;

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        validator=new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);

    }
    @OnClick(R.id.Login)
    void Login(){

        validator.validate();

    }
    @OnClick(R.id.UserRegister)
    void UserRegister(){
        Intent i = new Intent(Login.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onValidationSucceeded() {
        userlogin();
    }

    private void userlogin(){
        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse>loginResponseCall=apiInterface.LoginResponse("login",Userid.getText().toString(),
                UserPass.getText().toString());

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getSuccess()==200) {
                    Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainPage.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                Log.e("throwable message",t.getMessage());


            }
        });



    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);


            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            }
        }

    }
}
