package com.anlida.smartlock.model.resp;

public class RespRemoteToken {


    /**
     * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjAyODYxNjQsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNDhkMjVmNzYtMjExYS00MGUwLTg2NjAtOTgwZmFkMDU1ZWYxIiwiY2xpZW50X2lkIjoiYmxlc3NlZCIsInNjb3BlIjpbImFsbCJdfQ.npWuErC7vR3vagn8YIBdM2R4WEt6TF5i6HCUId4ZcIk
     * token_type : bearer
     * expires_in : 43199
     * scope : all
     * company : 芯林盛业
     */

    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private String company;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
