package com.mortal2019.okhttp3.functional;

import okhttp3.Call;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 网络响应
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/25 16:36
 */
@FunctionalInterface
public interface OnResponse {
    void onResponse(@NotNull Call call, @NotNull Response response) throws IOException;
}
