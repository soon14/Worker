package com.xsd.okhttp.retrofit2;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");


    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, value.toString());
    }
}
