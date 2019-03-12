package com.example.india.retrofitsilver;

import android.content.Intent;
import android.service.autofill.Validator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.india.retrofitsilver.Api_Manager.ApiClient;
import com.example.india.retrofitsilver.Api_Response.ViewResponse;
import com.example.india.retrofitsilver.Api_interface.ApiInterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener{
    /** ButterKnife Code **/
    @NotEmpty
    @BindView(R.id.DetailName)
    TextView DetailName;
    @NotEmpty
    @BindView(R.id.DetailPhoneno)
    TextView DetailPhoneno;
    @NotEmpty
    @BindView(R.id.DetailEmail)
    TextView DetailEmail;
    @NotEmpty
    @BindView(R.id.DetailPassword)
    TextView DetailPassword;
    /** ButterKnife Code **/
    com.mobsandgeeks.saripaar.Validator validator;
ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);

        validator=new com.mobsandgeeks.saripaar.Validator(this);
        validator.setValidationListener(this);
    }


    @Override
    public void onValidationSucceeded() {

    }

    private void userview(){

        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<ViewResponse> viewResponseCall=apiInterface.ViewResponse("view",
                DetailName.getText().toString(),
                DetailPhoneno.getText().toString(),
                DetailEmail.getText().toString(),
                DetailPassword.getText().toString());

        viewResponseCall.enqueue(new Callback<ViewResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ViewResponse> call, Response<ViewResponse> response) {
                if (response.body().getStatus()==200) {
                    Toast.makeText(MainPage.this, "Successfull", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    Toast.makeText(MainPage.this, "no detail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ViewResponse> call, Throwable t) {

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
                Toast.makeText(this, "Not Successful", Toast.LENGTH_SHORT).show();

            }

    }
}}
