package com.mortal2019.okhttp3;

import com.google.gson.Gson;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/25 16:43
 */
public class WebSocketManager {
    boolean err = false;
    boolean run = false;

    /**
     * 功能描述
     *
     * @param url               地址
     * @param webSocketListener 监听反馈
     * @return com.mortal2019.okhttp3.WebSocketManager
     */
    public static WebSocketManager create(String url, WebSocketListener webSocketListener) {
        return new WebSocketManager(url, webSocketListener);
    }

    String url;
    WebSocketListener webSocketListener;
    WebSocket webSocket;
    int restartMaxNum = 5;
    int restartNum = 0;

    private WebSocketManager(String url, WebSocketListener webSocketListener) {
        this.url = url;
        this.webSocketListener = webSocketListener;
    }

    /**
     * 设定最多重新启动的次数
     * @param num 次数
     */
    public void setRestartMaxNum(int num) {
        this.restartMaxNum = Math.max(num, 0);
    }

    public boolean start() {
        if (!run) {
            this.restartNum = 0;
            this.run(this.url);
            return true;
        }
        else {
            return false;
        }
    }
    public boolean stop() {
        if (run) {
            this.webSocket.close(0, "主动关闭");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 消息发送
     *
     * @param message 消息
     
     */
    public void send(String message) {
        if (this.run) {
            this.webSocket.send(message);
        }
    }

    /**
     * 对象发送
     *
     * @param jsonObject json对象
     
     */
    public void sendJson(Object jsonObject) {
        Gson gson = new Gson();
        String json = gson.toJson(jsonObject);
        this.send(json);
    }

    private void restart(){
        if(this.restartMaxNum==0 || this.restartNum<this.restartMaxNum){
            this.run(this.url);
        }
    }
    private void run(String url) {
        WebSocketListener selfWebSocketListener = new WebSocketListener() {
            /**
             * 关闭监听
             
             * @param webSocket webSocket
             * @param code      code
             * @param reason    reason
             */
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                run = false;
                webSocketListener.onClosed(webSocket, code, reason);
            }

            /**
             * 当远程对等端指示不再传输传入消息时调用。
             
             * @param webSocket webSocket
             * @param code      code
             * @param reason    reason
             */
            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                run = false;
                webSocketListener.onClosing(webSocket, code, reason);
            }

            /**
             * 异常调用
             
             * @param webSocket webSocket
             * @param t         错误
             * @param response  response
             */
            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                run = false;
                err = true;
                webSocketListener.onFailure(webSocket, t, response);
                restart();//重新启动
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                webSocketListener.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                webSocketListener.onMessage(webSocket, bytes);
            }

            /**
             * 开启调用
             
             * @param webSocket webSocket
             * @param response  response
             */
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                run = true;
                webSocketListener.onOpen(webSocket, response);
            }
        };
        this.webSocket = OkHttpUtil.webSocket(url, selfWebSocketListener);
    }

}
