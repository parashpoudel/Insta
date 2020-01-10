package com.example.insta.Insta.LoginForm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import InstaAPI.UserAPI;
import Model.LoginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Global;
import url.Url;

import com.example.insta.Insta.MainActivity;
import com.example.insta.R;


public class LoginFragment extends Fragment {

    private EditText etUsername,etPassword;
    private Button btnLogin;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = etUsername.getText().toString();
                String Password = etPassword.getText().toString();

                LoginModel loginModel = new LoginModel(Username,Password);


                UserAPI userAPI = Url.getInstance().create(UserAPI.class);
                Call<Model.Response> responseCall = userAPI.login(loginModel);

                responseCall.enqueue(new Callback<Model.Response>() {
                    @Override
                    public void onResponse(Call<Model.Response> call, Response<Model.Response> response) {
                        if(response.body().getSuccess()){
                            Global.username= response.body().getUsername();
                            Global.userImage= response.body().getUserImage();

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(getContext(),"Login Failed",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Model.Response> call, Throwable t) {
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return  view;


    }


}
