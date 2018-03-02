
package eu.napcode.popmovies.api.responsemodel.videos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseVideos {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("results")
    @Expose
    private List<ResponseVideo> responseVideos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ResponseVideo> getResponseVideos() {
        return responseVideos;
    }

    public void setResponseVideos(List<ResponseVideo> responseVideos) {
        this.responseVideos = responseVideos;
    }

}
