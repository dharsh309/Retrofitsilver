package com.example.india.retrofitsilver;

import android.service.autofill.Validator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements com.mobsandgeeks.saripaar.Validator.ValidationListener {
    /** ButterKnife Code **/
    @BindView(R.id.Userid)
    EditText Userid;
    @BindView(R.id.UserPass)
    EditText UserPass;
    /** ButterKnife Code **/
Validator validate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);




    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
