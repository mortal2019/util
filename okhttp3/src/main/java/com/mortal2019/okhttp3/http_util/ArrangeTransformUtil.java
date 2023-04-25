package com.mortal2019.okhttp3.http_util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mortal2019.okhttp3.exception.OkHttpException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 整理转换工具
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/25 16:37
 */
public class ArrangeTransformUtil {
    /**
     * 使用函数式将String转换成Map
     *
     * @param supplier 数据获取函数式
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> executeMap(Supplier<String> supplier) {
        String strJson = supplier.get();
        return getStringObjectMap(strJson);
    }

    /**
     * 将json字符串转换成Map
     *
     * @param strJson json字符串
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    @NotNull
    public static Map<String, Object> getStringObjectMap(String strJson) {
        try {
            Gson gson = new Gson();
            Map<String, Object> resultMap = gson.fromJson(strJson, new TypeToken<Map<String, Object>>() {
            }.getType());
            if (resultMap == null) {
                return new HashMap<>();
            } else {
                return mapDoubleToInt(resultMap);
            }
        } catch (JsonSyntaxException e) {
            throw new OkHttpException("Json'" + strJson + "'解析异常：" + e.getMessage());
        }
    }

    public static Map<String, Object> mapDoubleToInt(Map<?, ?> resultMap) {
        Map<String, Object> res = new HashMap<>(resultMap.size());
        for (Object keyObj : resultMap.keySet()) {
            String key = keyObj.toString();
            if (resultMap.get(key) instanceof Double) {
                Double value = (Double) resultMap.get(key);
                if (value.intValue() == value) {
                    res.put(key, ((Double) resultMap.get(key)).intValue());
                } else {
                    res.put(key, resultMap.get(key));
                }
            } else if (resultMap.get(key) instanceof List<?>) {
                res.put(key, listDoubleToInt((List<?>) resultMap.get(key)));
            } else if (resultMap.get(key) instanceof Map<?, ?>) {
                res.put(key, mapDoubleToInt((Map<?, ?>) resultMap.get(key)));
            } else {
                res.put(key, resultMap.get(key));
            }
        }
        return res;
    }

    public static List<Object> listDoubleToInt(List<?> list) {
        List<Object> res = new ArrayList<>(list.size());
        for (Object o : list) {
            if (o instanceof Number) {
                Double value = (Double) o;
                if (value.intValue() == value) {
                    Object v = value.intValue();
                    res.add(v);
                } else {
                    res.add(value);
                }
            } else if (o instanceof Map<?, ?>) {
                res.add(mapDoubleToInt((Map<?, ?>) o));
            } else if (o instanceof List<?>) {
                res.add(listDoubleToInt((List<?>) o));
            } else {
                res.add(o);
            }
        }
        return res;
    }

}
