package com.example.insta.Insta.Register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.loader.content.CursorLoader;

import InstaAPI.PhotosAPI;
import InstaAPI.UserAPI;
import Model.ImageResponse;
import Model.RegisterModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

import com.example.insta.Insta.LoginForm.LoginFragment;
import com.example.insta.Insta.MainActivity;
import com.example.insta.R;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static strictmode.StrictMode.StrictMode;

public class RegisterFragment extends Fragment {

    private EditText etName,etUsername, etAge, etEmail, etPhone;
    private EditText etPassword, etConfirmPassword;
    private ImageView profileimage;
    private  String profileImagepath, profileImagename;

    private Button btnSave;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(getContext(), "Please Select an image", Toast.LENGTH_LONG).show();
            }
        }
        Uri uri = data.getData();
        profileImagepath = getRealPathFromUri(uri);
        previewProfileImage(profileImagepath);
    }


    private void previewProfileImage(String profileImagepath) {
        File file = new File(profileImagepath);
        if (file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            profileimage.setImageBitmap(myBitmap);

        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_fragment, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

          ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,},0);

        }

        etName = view.findViewById(R.id.etName);
        etUsername = view.findViewById(R.id.etUsername);
        etAge = view.findViewById(R.id.etAge);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        profileimage = view.findViewById(R.id.profileimage);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }

        });



        btnSave = view.findViewById(R.id.btnSave);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveUser();





                emptyedittext();
                LoginFragment loginFrag = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.loginFrag, loginFrag,"Find this Fragment")
                        .addToBackStack(null)
                        .commit();

                Intent intent = new Intent(getContext(), LoginFragment.class);
                startActivity(intent);

            }
        });

        return view;


    }

    private void emptyedittext() {

        etName.setText("");
        etUsername.setText("");
        etAge.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        profileimage.setImageResource(R.drawable.ic_camera_alt_black_24dp);


    }



    private void saveUser() {

        String name = etName.getText().toString();
        String username= etUsername.getText().toString();
        String age = etAge.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String cpassword = etConfirmPassword.getText().toString();




        if (name.isEmpty()) {

            etName.setError("Field is empty");
        }
        else if (username.isEmpty()) {

            etName.setError("Field is empty");
        }

        else if(age.isEmpty()) {
            etAge.setError("Field is empty");
        }
        else if(email.isEmpty()) {
            etEmail.setError("Field is empty");
        }
        else if(phone.isEmpty()) {
            etPhone.setError("Field is empty");
        }
        else if(password.isEmpty()) {
            etPassword.setError("Field is empty");
        }
        else if(cpassword.isEmpty()) {
            etConfirmPassword.setError("Field is empty");
        }
        else if (!password.equals(cpassword)) {
            etConfirmPassword.setError("Password doesnot match");
            etPassword.setText("");
            etConfirmPassword.setText("");
        }
        saveImageOnly();
        RegisterModel registerModel = new RegisterModel(username, password, name, phone, email,profileImagename);
        UserAPI userAPI = Url.getInstance().create(UserAPI.class);
        Call<Model.Response> responseCall = userAPI.register(registerModel);
        responseCall.enqueue(new Callback<Model.Response>() {
            @Override
            public void onResponse(Call<Model.Response> call, Response<Model.Response> response) {
                if (response.body().getSuccess()) {

                    Toast.makeText(getContext(), "Register Success", Toast.LENGTH_LONG).show();



//

                } else {
                    Toast.makeText(getContext(), "Register Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Model.Response> call, Throwable t) {
                Toast.makeText(getContext(), "Error"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

        }

        public void saveImageOnly(){
            File file = new File(profileImagepath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);

            PhotosAPI photosAPI = Url.getInstance().create(PhotosAPI.class);
            Call<ImageResponse> responseBodyCall = photosAPI.uploadImage(body);

            StrictMode();

            try {
                Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
                //After saving an image, retrieve the current name of the image
                profileImagename = imageResponseResponse.body().getFilename();

            } catch (IOException e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }


}
