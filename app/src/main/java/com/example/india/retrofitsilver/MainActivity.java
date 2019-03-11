
package com.example.india.retrofitsilver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.india.retrofitsilver.Api_Manager.ApiClient;
import com.example.india.retrofitsilver.Api_Response.RegisterResponse;
import com.example.india.retrofitsilver.Api_interface.ApiInterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements Validator.ValidationListener{

    /** ButterKnife Code **/
    @NotEmpty
    @BindView(R.id.UserName)
    EditText UserName;

    @Length(max = 10,min = 10,message = "10 digits ")
    @BindView(R.id.PhoneNo)
    EditText PhoneNo;

    @Email
    @BindView(R.id.Email)
    EditText Email;

    @Password(message = "Minimum 6 character require")
    @BindView(R.id.Password)
    EditText Password;
    @BindView(R.id.Submit)
    Button Submit;
    /** ButterKnife Code **/

    Validator validator;

    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        validator=new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.Submit)
    void Submit(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        register();
           }

    private void register(){
    apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterResponse>registerResponseCall=apiInterface.RegisterResponse("register",UserName.getText().toString(),
                PhoneNo.getText().toString(),
                Email.getText().toString(),
                Password.getText().toString());


        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body().getStatus() == 1){
                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,Login.class);
                    startActivity(i);
                    finish();

                }
                else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Not registererd", Toast.LENGTH_SHORT).show();
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
