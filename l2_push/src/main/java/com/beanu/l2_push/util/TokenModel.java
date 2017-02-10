package com.beanu.l2_push.util;

import com.beanu.l2_push.PhoneTarget;

/**
 * Created by Beanu on 2017/2/10.
 */

public class TokenModel {
    private String mToken;
    private PhoneTarget mTarget;

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public PhoneTarget getTarget() {
        return mTarget;
    }

    public void setTarget(PhoneTarget mTarget) {
        this.mTarget = mTarget;
    }
}
