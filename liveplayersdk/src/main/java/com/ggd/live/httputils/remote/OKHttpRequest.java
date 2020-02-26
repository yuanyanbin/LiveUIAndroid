package com.ggd.live.httputils.remote;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ggd.live.R;
import com.ggd.live.httputils.constant.Constants;
import com.ggd.live.httputils.util.LogUtil;
import com.ggd.live.httputils.util.MD5;
import com.ggd.live.httputils.util.NetConnectUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import static com.ggd.live.httputils.constant.Constants.TYPE_GET;
import static com.ggd.live.httputils.constant.Constants.TYPE_POST_FORM;
import static com.ggd.live.httputils.constant.Constants.TYPE_POST_JSON;

/**
 * Copyright (C)
 * FileName: HttpManager
 * Author: 员外
 * Date: 2019-05-23 16:53
 * Description: TODO<Java类描述>
 * Version: 1.0
 */

public class OKHttpRequest {
    private static final String TAG = OKHttpRequest.class.getSimpleName();
    public static boolean STOP_DOWNLOAD;
    private static volatile OKHttpRequest mInstance;//单利引用
    Request request = null;
    long startTime = 0;//接口请求时间
    long endTime = 0;//接口响应时间
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private Context context;
    private boolean unique = true;  //防止重复请求  true：可以请求  false：不可请求
    Handler okHttpHandler = new Handler(Looper.getMainLooper());

    public OKHttpRequest(Context context) {
        this.context = context;
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()//.proxy(Proxy.NO_PROXY)
                .connectTimeout(30, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//设置写入超时时间
                .cookieJar(CookiesManager.getInstance(context))
                .build();
    }

    public static OKHttpRequest getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OKHttpRequest.class) {
                if (mInstance == null) {
                    mInstance = new OKHttpRequest(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressCallBack listener) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    //sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);
                        LogUtil.e("requestUrl=====上传" + "===totalBytes==" + contentLength() + "\n"
                                + "==remainingBytes===" + remaining);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("requestUrl=====上传" + "====Exception=====" + e.toString());
                }
            }
        };
    }

    /**
     * okHttp异步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     * @param callBack    请求返回数据回调
     * @param <T>         数据泛型
     **/
    public <T> void requestAsyn(final String actionUrl, int requestType, final HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        startTime = System.currentTimeMillis();
        boolean isNet = NetConnectUtil.isNetConnected(context);
        if (!isNet) {
            failedCallBack(-1001, context.getString(R.string.tips_no_network), callBack);
            return;
        }

        try {
            switch (requestType) {
                case TYPE_GET:
                    request = OkHttpUtils.requestGetByAsyn(context, actionUrl, paramsMap);
                    break;
                case TYPE_POST_JSON:
                    request = OkHttpUtils.requestPostByAsyn(context, actionUrl, paramsMap);
                    break;
                case TYPE_POST_FORM:
                    request = OkHttpUtils.requestPostByAsynWithForm(context, actionUrl, paramsMap);
                    break;
            }
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    endTime = System.currentTimeMillis();
                    if ("connect timed out".equals(e.getMessage())) {
//                        failedCallBack("服务器连接超时，过会儿再试试", callBack);
                        failedCallBack(-1000, context.getString(R.string.tips_request_server_fail), callBack);
                    } else {
                        failedCallBack(-1000, context.getString(R.string.tips_request_server_fail), callBack);
                    }
                    LogUtil.e("request ----->" + call.request().url() + "\n" +
                            "requestType ----->" + call.request().method() + "\n" +
                            "paramsMap ----->" + paramsMap.toString() + "\n" +
                            " -----response ----->" + "访问失败" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    endTime = System.currentTimeMillis();
                    LogUtil.e("----------------------------------------------" + response.code());
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtil.e("request ----->" + response.request().url() + "\n" +
                                "requestType ----->" + response.request().method() + "\n" +
                                "paramsMap ----->" + paramsMap.toString() + "\n" +
                                "startTime ----->" + startTime + "\n" + "endTime ----->" + endTime + "\n" + "endTime-startTime ----->" + (endTime - startTime) + "\n" +
                                " -----response ----->" + string);
                        isSuccess(string, callBack);
                    } else {
                        failedCallBack(-1000, context.getString(R.string.tips_request_server_fail), callBack);
                        LogUtil.d(TAG, response.toString());
                        LogUtil.e("request ----->" + response.request().url() + "\n" +
                                "requestType ----->" + response.request().method() + "\n" +
                                "paramsMap ----->" + paramsMap.toString() + "\n" +
                                "startTime ----->" + startTime + "\n" + "endTime ----->" + endTime + "\n" + "接口请求时间Time ----->" + (endTime - startTime) + "\n" +
                                " -----response ----->" + "服务器错误");
                    }
                }
            });
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            LogUtil.e("request ----->" + request + "\n" +
                    "paramsMap ----->" + paramsMap.toString() + "\n" +
                    "responseUrl ----->" + actionUrl + "\n" +
                    "startTime ----->" + startTime + "\n" + "endTime ----->" + endTime + "\n" + "接口请求时间Time ----->" + (endTime - startTime) + "\n" +
                    " -----response ----->" + e.toString());
            failedCallBack(-1000, "", callBack); //不在显示错误日志
        }
    }

    boolean isT = false;    //token err之前是否登录成功过

    /**
     * 判断返回是否成功
     **/
    protected void isSuccess(String responseResult, final ReqCallBack callBack) {
        try {
            JSONObject jsonObject = new JSONObject(responseResult);
            int success = jsonObject.optInt("code");
            final String errorMsg = jsonObject.optString("message");
            switch (success) {
                case 0://操作成功
                    successCallBack(responseResult, callBack);
                    break;
                default:
                    //操作失败（提示给用户）
                    failedCallBack(success, errorMsg, callBack);
                    break;
            }
        } catch (Exception e) {
            LogUtil.e("服务器返回错误信息------>" + e.toString());
            failedCallBack(3000, context.getResources().getString(R.string.tips_request_server_fail), callBack);
        }
    }

    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        if (callBack != null) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onReqSuccess(result);
                }
            });

        }
    }

    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final int errCode, final String errorMsg, final ReqCallBack<T> callBack) {
        if (callBack != null) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onReqFailed(errCode, errorMsg);
                }
            });
        }
    }

    /**
     * 带参数上传文件
     *
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack  回调
     * @param <T>
     */
    public <T> void upLoadFile(final String actionUrl, HashMap<String, Object> paramsMap, final ProgressCallBack callBack) {
        try {
            String mBaseUrl = Constants.appBaseUrl;
            //补全请求地址
            String requestUrl = String.format("%s/%s", mBaseUrl, actionUrl);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    //builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                    builder.addFormDataPart(key, file.getName(), createCustomRequestBody(MultipartBody.FORM, file, callBack));

                }
            }
            //创建RequestBody
            RequestBody body = builder.build();

            //创建Request
//            final Request request = new Request.Builder()
//                    .header("token",token)
//                    .url(requestUrl).post(body).build();
            final Request request = OkHttpUtils.addHeaders(context).url(requestUrl).post(body).build();
            //单独设置参数 比如读取超时时间
            final Call call = mOkHttpClient.newBuilder()
                    .connectTimeout(500000, TimeUnit.SECONDS)
                    .readTimeout(500000, TimeUnit.SECONDS)
                    .writeTimeout(500000, TimeUnit.SECONDS)
                    .build()
                    .newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.e("requestUrl=====" + actionUrl + "====onFailure=====" + e.toString());
                    if (callBack != null) {
                        callBack.onReqFailed(-1000, "上传失败");
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtil.e("requestUrl=====" + actionUrl + "====onResponse=====" + string);
                        if (callBack != null) {
                            callBack.onReqSuccess(string);
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onReqFailed(-1000, "上传失败");
                        }
                        LogUtil.e("requestUrl=====" + actionUrl + "====onFailure=====上传失败");
                    }
                }
            });
        } catch (Exception e) {
            LogUtil.e("requestUrl=====" + actionUrl + "====Exception=====" + e.toString());
        }
    }

    /**
     * 下载文件
     *
     * @param fileUrl     文件url
     * @param destFileDir 存储目标目录
     */
    public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqProgressCallBack<T> callBack) {
//        String suffix;
//        if (fileUrl.contains("?")) {
//            suffix = fileUrl.substring(fileUrl.lastIndexOf("."),fileUrl.indexOf("?"));
//        } else {
//            suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
//        }

        final String fileName = MD5.Encode(fileUrl)/*+suffix*/;
        final File file = new File(destFileDir, fileName);
        if (file.exists()) {
//            successCallBack((ToastUtil) file, callBack);
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, e.toString());
                failedCallBack(-1000, "下载失败", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    LogUtil.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        if (STOP_DOWNLOAD) {
                            file.delete();
                            return;
                        }
                        current += len;
                        fos.write(buf, 0, len);
                        progressCallBack(total, current, callBack);
                    }
                    fos.flush();
                    successCallBack((T) file, callBack);
                } catch (IOException e) {
                    LogUtil.e(TAG, e.toString());
                    failedCallBack(-1000, "下载失败", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        LogUtil.e(TAG, e.toString());
                    }
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @param fileUrl     文件url
     * @param destFileDir 存储目标目录
     * @param fileName    存储的文件名
     */
    public <T> void downLoadFile(String fileUrl, final String destFileDir, final String fileName, final ReqProgressCallBack<T> callBack) {
//        String suffix;
//        if (fileUrl.contains("?")) {
//            suffix = fileUrl.substring(fileUrl.lastIndexOf("."),fileUrl.indexOf("?"));
//        } else {
//            suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
//        }
        final File file = new File(destFileDir, fileName);
        if (file.exists()) {
//            successCallBack((ToastUtil) file, callBack);
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, e.toString());
                failedCallBack(-1000, "下载失败", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    LogUtil.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        if (STOP_DOWNLOAD) {
                            file.delete();
                            return;
                        }
                        current += len;
                        fos.write(buf, 0, len);
                        progressCallBack(total, current, callBack);
                    }
                    fos.flush();
                    successCallBack((T) file, callBack);
                } catch (IOException e) {
                    LogUtil.e(TAG, e.toString());
                    failedCallBack(-1000, "下载失败", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        LogUtil.e(TAG, e.toString());
                    }
                }
            }
        });
    }

    /**
     * 统一处理进度信息
     *
     * @param total    总计大小
     * @param current  当前进度
     * @param callBack
     * @param <T>
     */
    private <T> void progressCallBack(final long total, final long current, final ReqProgressCallBack<T> callBack) {
        if (callBack != null) {
            callBack.onProgress(total, current);
        }
    }

    public interface ReqProgressCallBack<T> extends ReqCallBack<T> {
        /**
         * 响应进度更新
         */
        void onProgress(long total, long current);
    }
}
