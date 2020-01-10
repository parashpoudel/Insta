package fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.insta.R;

import java.util.ArrayList;
import java.util.List;

import InstaAPI.PostAPI;
import Model.UploadModel;
import adapter.FeedsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment {
    private RecyclerView rvfeeds;
    List<UploadModel> postList = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feeds, container, false);
        rvfeeds = view.findViewById(R.id.rvfeeds);

        PostAPI postAPI = Url.getInstance().create(PostAPI.class);
        Call<List<UploadModel>> responseCall = postAPI.getPost();

        responseCall.enqueue(new Callback<List<UploadModel>>() {
            @Override
            public void onResponse(Call<List<UploadModel>> call, Response<List<UploadModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();

                }
                else {

                   // Toast.makeText(getContext(),"Parash",Toast.LENGTH_LONG).show();
                    postList= response.body();

                    FeedsAdapter feedsAdapter = new FeedsAdapter(getContext(), postList);
                    rvfeeds.setAdapter(feedsAdapter);
                    rvfeeds.setNestedScrollingEnabled(false);
                    rvfeeds.setLayoutManager(new LinearLayoutManager(getContext()));


                }
            }

            @Override
            public void onFailure(Call<List<UploadModel>> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });


       return view;
    }

}
