package co.appdev.boilerplate.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("business_name")
    private String businessName;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("business_type")
    private String businessType;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    @SerializedName("status")
    private String status;

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "business_name = '" + businessName + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",business_type = '" + businessType + '\'' +
                        ",mobile = '" + mobile + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}
