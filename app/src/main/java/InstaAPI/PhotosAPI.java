package InstaAPI;

import Model.ImageResponse;
import Model.UploadModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PhotosAPI {
    @Multipart
    @POST("api/account/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);


}
