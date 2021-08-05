package com.telran.hiducation.utills;

import java.util.Base64;

public class ProcessHashcode {

    public String encodeStr(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public String decodeHash(String hash) {
        return new String(Base64.getDecoder().decode(hash));
    }

}
