package com.mortal2019.okhttp3.functional;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 网络错误
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/25 16:33
 */
@FunctionalInterface
public interface OnFailure {
    void onFailure(@NotNull Call call, @NotNull IOException e);
}
