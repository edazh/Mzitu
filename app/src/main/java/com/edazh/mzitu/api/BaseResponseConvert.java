package com.edazh.mzitu.api;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by edazh on 2018/1/11 0011.
 */

public abstract class BaseResponseConvert<T> implements Converter<ResponseBody, T> {
    @Override
    public T convert(ResponseBody value) throws IOException {
        return parserHtml(value.string());
    }

    protected abstract T parserHtml(String html);
}
