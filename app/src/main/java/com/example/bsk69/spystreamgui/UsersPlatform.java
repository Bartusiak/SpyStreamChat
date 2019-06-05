package com.example.bsk69.spystreamgui;

public class UsersPlatform {
    private String userId;
    private String token;

    public UsersPlatform(String userId, String token){
        this.userId=userId;
        this.token=token;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }


}
