package com.example.demo.exception;

//import com.sun.xml.internal.messaging.saaj.util.Base64;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by 超男 on 2019/3/5.
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super("验证码错误或为空");
    }

    public static String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8")), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "scmd2019" + ":" + "0306";
        String base64 = getBase64(s);
        System.out.println(base64);

    }
}
