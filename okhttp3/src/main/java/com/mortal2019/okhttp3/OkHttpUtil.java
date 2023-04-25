package com.mortal2019.okhttp3;

import com.mortal2019.okhttp3.exception.OkHttpException;
import com.mortal2019.okhttp3.functional.OnFailure;
import com.mortal2019.okhttp3.functional.OnResponse;
import com.mortal2019.okhttp3.http_util.ArrangeTransformUtil;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * 2023/4/25 16:39
 */
public class OkHttpUtil {
    public static final String MEDIA_TYPE_APPLICATION_JSON_UTF_8 = "application/json;charset=utf-8";
    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";

    private final OkHttpClient.Builder client;
    private final Request.Builder request;
    private final HttpUrl.Builder httpUrl;
    private final FormBody.Builder bodyBuilder;
    private MultipartBody.Builder multipartBody;
    private MethodModel methodModel;
    private RequestBody requestBody;

    /**
     * 提供已经包装的OkHttp3原型
     *
     * @return com.mortal2019.okhttp3.OkHttpUtil.OkHttpPrimeval
     */
    public OkHttpPrimeval getOkHttpPrimeval() {
        return new OkHttpPrimeval();
    }

    class OkHttpPrimeval {
        public OkHttpClient.Builder getOkHttpClientBuilder() {
            return client;
        }

        public Request.Builder getRequestBuilder() {
            return request;
        }

        public HttpUrl.Builder getHttpUrlBuilder() {
            return httpUrl;
        }

        public FormBody.Builder getBodyBuilderBuilder() {
            return bodyBuilder;
        }

        public MethodModel getMethodModel() {
            return methodModel;
        }

        public RequestBody getRequestBody() {
            return requestBody;
        }
    }

    public static OkHttpUtil create(String url) {
        return new OkHttpUtil(url);
    }

    private OkHttpUtil(String url) {
        this.httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        this.request = new Request.Builder();
        this.client = new OkHttpClient.Builder();
        this.bodyBuilder = new FormBody.Builder();
        this.methodModel = MethodModel.GET;
    }

    /**
     * 连接超时
     *
     * @param seconds 秒
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil connectTimeout(int seconds) {
        this.client.connectTimeout(seconds, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 读取超时
     *
     * @param seconds 秒
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil readTimeout(int seconds) {
        this.readTimeout = seconds;
        this.client.readTimeout(seconds, TimeUnit.SECONDS);
        return this;
    }

    int readTimeout;

    /**
     * 写入超时
     *
     * @param seconds 秒
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil writeTimeout(int seconds) {
        this.client.writeTimeout(seconds, TimeUnit.SECONDS);
        return this;
    }


    /**
     * 添加地址参数
     *
     * @param name  键值
     * @param value 值
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil addQueryParameter(String name, String value) {
        this.httpUrl.addQueryParameter(name, value);
        return this;
    }

    /**
     * 添加body参数
     *
     * @param name  参数名称
     * @param value 参数值
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil addBody(String name, String value) {
        this.bodyBuilder.add(name, value);
        this.post();
        return this;
    }

    /**
     * 添加form
     *
     * @param name  名称
     * @param value 值
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil addFormDataPart(String name, String value) {
        //传递键值对参数
        if (multipartBody == null) {
            this.multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        }
        this.multipartBody.addFormDataPart(name, value);
        this.post();
        return this;
    }

    /**
     * 添加formData
     *
     * @param name     名称
     * @param filename 文件名
     * @param body     RequestBody
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil addFormDataPart(String name, String filename, RequestBody body) {
        if (multipartBody == null) {
            this.multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        }
        this.multipartBody.addFormDataPart(name, filename, body);
        this.post();
        return this;
    }

    /**
     * 字符串设置Body
     *
     * @param bodyString body字符串
     * @param method     MediaType
     * @return com.mortal2019.okhttp3.OkHttpUtil
     * @throws IllegalArgumentException 参数不能设置成空
     */
    public OkHttpUtil setBody(String bodyString, String method) {
        if (bodyString == null || method == null) {
            throw new IllegalArgumentException("参数不能为空");
        } else {
            this.requestBody = RequestBody.create(bodyString, MediaType.parse(method));
            this.post();
        }
        return this;
    }

    /**
     * 模式
     */
    public enum MethodModel {
        /**
         * get方法
         */
        GET,
        /**
         * post方法
         */
        POST,
        /**
         * delete方法
         */
        DELETE,
        /**
         * put方法
         */
        PUT,
        /**
         * patch方法
         */
        PATCH,
        /**
         * head方法
         */
        HEAD,
        ;

        public String getName() {
            return this.name();
        }

        void builder(Request.Builder builder, RequestBody requestBody) {
            if (HttpMethod.permitsRequestBody(this.name())) {
                builder.method(this.getName(), requestBody);
            } else {
                builder.method(this.name(), null);
            }
        }
    }

    //region 执行模式设定

    /**
     * 设定执行模式
     *
     * @param methodModel GET, POST, DELETE, PUT, PATCH, HEAD
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil setMethodModel(OkHttpUtil.MethodModel methodModel) {
        this.methodModel = methodModel;
        return this;
    }

    /**
     * get模式
     *
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil get() {
        this.methodModel = MethodModel.GET;
        return this;
    }

    /**
     * post模式
     *
     * @return com.mortal2019.okhttp3.OkHttpUtil
     */
    public OkHttpUtil post() {
        this.methodModel = MethodModel.POST;
        return this;
    }
    //endregion

    //region 执行查询

    /**
     * 执行查询
     *
     * @return okhttp3.Response
     */
    public Response execute() {
        this.request.url(httpUrl.build());
        if (this.multipartBody != null) {
            this.request.post(this.multipartBody.build());
        }
        OkHttpClient client = this.getOkHttpClient();
        Request request = this.request.build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String executeToString() {
        try {
            String resData;
            try (Response response = this.execute()) {
                resData = Objects.requireNonNull(response.body()).string();
            }
            return resData;
        } catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executeToMap() {
        return ArrangeTransformUtil.executeMap(this::executeToString);
    }

    //endregion

    //region 异步执行

    /**
     * 异步执行提交
     *
     * @param onResponse 返回信息
     * @param onFailure  异常
     */
    public void asynchronousExecute(OnResponse onResponse, OnFailure onFailure) {
        this.request.url(httpUrl.build());
        OkHttpClient client = getOkHttpClient();
        Request request = this.request.build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (onFailure != null) {
                    onFailure.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (onResponse != null) {
                    onResponse.onResponse(call, response);
                }
            }
        };
        client.newCall(request).enqueue(callback);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient client = this.client.build();
        if (this.requestBody == null) {
            this.requestBody = this.bodyBuilder.build();
        }
        this.methodModel.builder(this.request, this.requestBody);
        return client;
    }

    //region webSocket
    public static WebSocket webSocket(String url, WebSocketListener webSocketListener) {
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
        Request request = new Request.Builder().url(url).build();
        client.dispatcher().cancelAll();//清理一次

        return client.newWebSocket(request, webSocketListener);
    }
    //endregion


    /**
     * 异步执行返回 String
     *
     * @param consumer  String消费
     * @param onFailure 异常
     */
    public void asynchronousExecuteToString(Consumer<String> consumer, OnFailure onFailure) {
        this.asynchronousExecute((call, response) -> {
            String str = Objects.requireNonNull(response.body()).string();
            response.close();
            consumer.accept(str);
        }, onFailure);
    }

    /**
     * 异步执行返回 Map
     *
     * @param consumer  Map消费
     * @param onFailure 异常
     */
    public void asynchronousExecuteToMap(Consumer<Map<String, Object>> consumer, OnFailure onFailure) {
        asynchronousExecuteToString((str) -> consumer.accept(ArrangeTransformUtil.executeMap(() -> str)), onFailure);
    }
    //endregion

}
