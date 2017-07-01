package com.vcf.przemek.wordsmemorizer.custom_oauth;

/**
 * Created by Przemek on 2017-07-01.
 */

public class CustomOAuthToken {

    private CustomOAuthToken() {
    }

    public void setValues(String access_token, String token_type, String expires_in, String refresh_token, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.scope = scope;
    }

    private static CustomOAuthToken instance = null;

    public static CustomOAuthToken getInstance() {
        if(instance == null) {
            instance = new CustomOAuthToken();
        }
        return instance;
    }

    private String access_token;
    private String token_type;
    private String expires_in;
    private String scope;
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
