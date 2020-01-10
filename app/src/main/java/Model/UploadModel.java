package Model;

public class UploadModel {
    private String postImage;
    private String username;
    private String caption;
    private String poster_image;

    public UploadModel(String postImage, String username, String caption, String poster_image) {
        this.postImage = postImage;
        this.username = username;
        this.caption = caption;
        this.poster_image = poster_image;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(String poster_image) {
        this.poster_image = poster_image;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

