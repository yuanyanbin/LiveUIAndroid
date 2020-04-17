package com.baijiayun.live.httputils.remote;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Copyright (C)
 * FileName: HttpManager
 * Author: 员外
 * Date: 2019-05-23 16:53
 * Description: TODO<自动管理Cookies>
 * Version: 1.0
 */
public class CookiesManager implements CookieJar {
    public static CookiesManager mInstance = null;
    private PersistentCookieStore cookieStore = null;

    public CookiesManager(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }

    public static CookiesManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CookiesManager.class) {
                if (mInstance == null) {
                    mInstance = new CookiesManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public List<Cookie> getCookies(HttpUrl url){
        List<Cookie> cookies = cookieStore.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
