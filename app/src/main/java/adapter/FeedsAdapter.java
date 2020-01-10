package adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insta.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import Model.UploadModel;
import de.hdodenhof.circleimageview.CircleImageView;
import url.Url;

import static strictmode.StrictMode.StrictMode;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.FeedsAdapterViewHolder> {

    Context mContext;
  public static List<UploadModel> feedlist;

    public FeedsAdapter(Context mContext, List<UploadModel> feedlist) {
        this.mContext = mContext;
        this.feedlist = feedlist;
    }

    @NonNull
    @Override
    public FeedsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feeds,parent,false);
        return new FeedsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedsAdapterViewHolder holder, int position) {
        final UploadModel uploadModel = feedlist.get(position);

        final String posterImagePath = Url.BASE_URL + "/uploads/" + uploadModel.getPoster_image();
        String postImgPath = Url.BASE_URL + "/uploads/" + uploadModel.getPostImage();

        StrictMode();

        try {
            URL url = new URL(postImgPath);
            holder.photo.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
            URL url1 = new URL(posterImagePath);
            holder.posterimage.setImageBitmap(BitmapFactory.decodeStream((InputStream)url1.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.uploadername.setText(uploadModel.getUsername());
        holder.caption.setText(uploadModel.getCaption());

//        feedlist.remove(position);
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return feedlist.size();
    }

    public class FeedsAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView uploadername, caption;
        ImageView photo;
        CircleImageView posterimage;

        public FeedsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            uploadername= itemView.findViewById(R.id.uploadername);
            photo= itemView.findViewById(R.id.photo);
            caption= itemView.findViewById(R.id.caption);
            posterimage= itemView.findViewById(R.id.posterimage);

        }
    }

}
