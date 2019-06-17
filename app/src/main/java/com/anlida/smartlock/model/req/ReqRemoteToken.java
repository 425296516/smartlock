package com.anlida.smartlock.model.req;

public class ReqRemoteToken {

    private String grant_type;
    private String username;
    private String password;
    private String scope;

    public ReqRemoteToken(String grant_type, String username, String password, String scope) {
        this.grant_type = grant_type;
        this.username = username;
        this.password = password;
        this.scope = scope;
    }

}
