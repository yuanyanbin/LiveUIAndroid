package com.ggd.live.httputils.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Mr.xu on 2018/11/1.
 */

public class GsonUtil {
    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(String.class, new StringTypeAdapter())//处理后台null
                .create();
    }
}
