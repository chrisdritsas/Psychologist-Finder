package com.example.present.Controllers;

import android.util.Log;

public final class ConnectionResult {
    private String message;
    private String tag;
    private int messageType;
    private boolean result;
    private int code;
    private Object obj;

    public ConnectionResult() {
    }

    public ConnectionResult(String tag) {
        this.tag=tag;
    }

    public ConnectionResult(String message, boolean result, String tag, int code, Object obj) {
        this.message = message;
        this.result = result;
        this.tag = tag;
        this.code = code;
        this.obj = obj;
    }

    public void showInLog(){
        if(this.messageType ==0) {
            Log.d(this.getTag(), this.getMessage());
        } else if (this.messageType ==1) {
            Log.w(this.getTag(), this.getMessage());
        }else if(this.messageType ==2){
            Log.e(this.getTag(), this.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ConnectionResult{" +
                "message='" + message + '\'' +
                ", tag='" + tag + '\'' +
                ", messageType=" + messageType +
                ", result=" + result +
                ", code=" + code +
                ", obj=" + obj +
                '}';
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isResult() {
        return result;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
