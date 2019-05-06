package com.example.unishop.pincode;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WResponse {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("PostOffice")
    @Expose
    private List<com.example.unishop.pincode.PostOffice> postOffice = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<com.example.unishop.pincode.PostOffice> getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(List<com.example.unishop.pincode.PostOffice> postOffice) {
        this.postOffice = postOffice;
    }

}
