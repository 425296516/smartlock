package com.anlida.smartlock.network.convert;

import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.network.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class FMResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    FMResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    /**
     * 根据code将正常与错误的业务逻辑隔离
     * @param value
     * @return
     * @throws IOException
     */
    @Override public T convert(ResponseBody value) throws IOException {
        String response=value.string();
        HttpResult result=gson.fromJson(response,HttpResult.class);
        if(!result.isNormal()){
            value.close();
            throw new ApiException(result.getCode(),result.getMsg());
        }
        MediaType type = value.contentType();
        Charset charset = type != null ? type.charset(UTF_8) : UTF_8;
        InputStream is = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(is, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
