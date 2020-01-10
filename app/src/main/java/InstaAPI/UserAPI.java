package InstaAPI;




import Model.LoginModel;
import Model.RegisterModel;
import Model.Response;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("api/account/register")
    Call<Response>register(@Body RegisterModel registerModel);

    @POST("api/account/login")
    Call<Response>login(@Body LoginModel loginModel);
}




