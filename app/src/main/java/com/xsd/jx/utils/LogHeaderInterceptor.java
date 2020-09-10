package com.xsd.jx.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Date: 2019/11/25
 * author: SmallCake
 *
 * 在请求header中，携带键  Authorization  值 Bearer UHEmyw2gB3wD_mJoEncZ-v4vKO2mmK52
 * 值 “Bearer UHEmyw2gB3wD_mJoEncZ-v4vKO2mmK52”后面的字符串就为  登录成功的access_token
 */
public class LogHeaderInterceptor implements Interceptor {
    Charset UTF8 = Charset.forName("UTF-8");

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) {
        Response response = null;
        try {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();

            //如果用户已经登录，头部加入auth
            String auth = "Bearer "+UserUtils.getToken();
            if (!TextUtils.isEmpty(auth)){
                Request.Builder requestBuilder = request.newBuilder();
                request = requestBuilder.addHeader("Authorization", auth).build();
            }

            long t1 = System.nanoTime();//请求发起的时间
            if (request.method().equals("POST")) iAll("SmallOkHttp>>>", String.format("【"+request.method()+"】发送请求 %s \n" +
                            "\n====================================参数开始==============================================\n" +
                            "%s"+
                            "\n====================================参数结束==============================================\n"
                    , request.url(), logParams(request)));
            response = chain.proceed(request);

            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            iAll("SmallOkHttp>>>",String.format("接收响应: %s" +
                            "\n====================================返回json开始==============================================\n" +
                            "%s" +
                            "\n====================================返回json结束==============================================\n" +
                            "【%.1fms】%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String logParams(Request request) throws IOException {
        String method = request.method();
        if ("POST".equals(method)) {
            RequestBody requestBody = request.body();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            String paramsStr = buffer.readString(UTF8);
            return paramsStr;
        }
        return "没有参数";


    }

    /**
     * if your logmessage too much,you can take this
     * @param msg
     */
    private static int LOG_MAXLENGTH = 2000;
    public static void iAll(String TAG,String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 47; i++) {
            if (strLength > end) {
                Log.i(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.i(TAG + i, msg.substring(start, strLength));
                break;
            }
        }

    }
}
