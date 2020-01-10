package fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.insta.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import InstaAPI.PhotosAPI;
import InstaAPI.PostAPI;
import Model.ImageResponse;
import Model.UploadModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Global;
import url.Url;

import static android.app.Activity.RESULT_OK;
import static strictmode.StrictMode.StrictMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {

        private ImageView uploadImage;
        private EditText etCaption;
        private Button btnUpload;
    private  String profileImagepath, profileImagename;

    public UploadFragment() {
        // Required empty public constructor
    }

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
        Toast.makeText(getContext(),profileImagepath, Toast.LENGTH_LONG).show();
        previewProfileImage(profileImagepath);
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

    private void previewProfileImage(String profileImagepath) {
        File file = new File(profileImagepath);
        if (file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            uploadImage.setImageBitmap(myBitmap);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_upload, container, false);
        uploadImage= view.findViewById(R.id.uploadImage);
        etCaption= view.findViewById(R.id.etCaption);


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }

        });


        btnUpload = view.findViewById(R.id.btnUpload);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                savePhoto();
            }
        });

        return view;

    }

    private void savePhoto() {

        saveImageOnly();
        String caption = etCaption.getText().toString();

        if (caption.isEmpty()) {

            etCaption.setError("Field is empty");
        }

        UploadModel uploadModel = new UploadModel(profileImagename,Global.username, caption, Global.userImage);
        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
        Call<Void> responseCall = postAPI.addPost(uploadModel);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getContext(),"Upload Successful",Toast.LENGTH_LONG).show();

                    FeedsFragment feedsfrag = new FeedsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.rvfeeds, feedsfrag,"Find this Fragment")
                            .addToBackStack(null)
                            .commit();

                    Intent intent = new Intent(getContext(), FeedsFragment.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "Upload Failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });





    }
    public void saveImageOnly(){
        File file = new File(profileImagepath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);

        PhotosAPI photosAPI = Url.getInstance().create(PhotosAPI.class);
        Call<ImageResponse> responseBodyCall =photosAPI.uploadImage(body);

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
