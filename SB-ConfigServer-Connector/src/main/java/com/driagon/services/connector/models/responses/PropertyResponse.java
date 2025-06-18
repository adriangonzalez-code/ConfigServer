package com.driagon.services.connector.models.responses;

import java.io.Serial;
import java.io.Serializable;

public class PropertyResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -689703783216361050L;

    private String key;
    private String value;
    private boolean secret;

    public PropertyResponse() {
    }

    public PropertyResponse(String key, String value, boolean secret) {
        this.key = key;
        this.value = value;
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }
}