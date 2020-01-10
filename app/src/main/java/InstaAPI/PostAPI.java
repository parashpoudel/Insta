package InstaAPI;

import java.util.List;

import Model.ImageResponse;
import Model.UploadModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostAPI {

    @POST("api/post-auth/addPost")
    Call<Void> addPost(@Body UploadModel uploadModel);
    @GET("api/post-auth/post")
    Call<List<UploadModel>> getPost();

}
