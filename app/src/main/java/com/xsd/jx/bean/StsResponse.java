package com.xsd.jx.bean;

/**
 * Date: 2020/10/20
 * author: SmallCake
 */
public class StsResponse {


    /**
     * AccessKeySecret : HPzNaSrf76hV2k67ivYfwp4Y3mWwvR45s4B7NF1kgo1A
     * Expiration : 2020-10-20T02:18:30Z
     * AccessKeyId : STS.NUPiUAzrNhLjdKJhPQ7gwpopW
     * SecurityToken : CAIS7QF1q6Ft5B2yfSjIr5blIu/1l61v346BZm37jFAEO+hbn6rbtTz2IH1EfXBsCekXtv42mGtU5voelqFxSpZDSlzFa5OMD0ihTUXzDbDasumZsJYm6vT8a0XxZjf/2MjNGZabKPrWZvaqbX3diyZ32sGUXD6+XlujQ/br4NwdGbZxZASjaidcD9p7PxZrrNRgVUHcLvGwKBXn8AGyZQhKwlMi0jMjuPvvmpzCsUGB0gbAp7VL99irEP+NdNJxOZpzadCx0dFte7DJuCwqsEUarf4q3PUVoW2e4orBUwUN+XucOu/T6cZ/p8Sz8QeUh8QagAEzyqxznZg6Nqz7wYLBKWKSJBH2gaVbCY8qaKUHJFWgu5H6yYYe0hYsX1GnNcVITraH/GHSNF1GZL+Zl0PaS7f+F+twfz2p/3CFe+U7C7Bv0/uxgYxlGez998dVehPWD4Vx7IR5DvUH9ER3lhZDbN/9slWBpk6x4ZK//MQW/kZetw==
     */

    private String AccessKeySecret;
    private String Expiration;
    private String AccessKeyId;
    private String SecurityToken;

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String AccessKeySecret) {
        this.AccessKeySecret = AccessKeySecret;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String Expiration) {
        this.Expiration = Expiration;
    }

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String AccessKeyId) {
        this.AccessKeyId = AccessKeyId;
    }

    public String getSecurityToken() {
        return SecurityToken;
    }

    public void setSecurityToken(String SecurityToken) {
        this.SecurityToken = SecurityToken;
    }
}
