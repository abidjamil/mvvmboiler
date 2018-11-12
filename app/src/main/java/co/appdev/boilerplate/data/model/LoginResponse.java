package co.appdev.boilerplate.data.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

    @SerializedName("status_code")
    private int statusCode;

    @SerializedName("data")
    private LoginData data;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }

    public void setData(LoginData data){
        this.data = data;
    }

    public LoginData getData(){
        return data;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "LoginResponse{" +
                        "status_code = '" + statusCode + '\'' +
                        ",data = '" + data + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}
