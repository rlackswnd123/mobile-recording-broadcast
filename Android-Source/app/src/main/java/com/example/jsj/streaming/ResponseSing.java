package com.example.jsj.streaming;

/**
 * Created by jsj on 2017-10-28.
 */

public class ResponseSing {

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }

    public String getResponse_password() {
        return response_password;
    }

    public void setResponse_password(String response_password) {
        this.response_password = response_password;
    }

    private String response_id;
    private String response_password;

    @Override
    public String toString(){
        return "ClassPojo [response_id"+response_id+",response_pase"+response_password+"]";
    }
}
