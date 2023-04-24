package com.mortal2019.util.map;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mortal2019.core.functional.MapToT;
import org.apache.commons.beanutils.BeanUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 14:53
 */
public class MapUtil {

    boolean throwException = false;

    public boolean isThrowException() {
        return throwException;
    }

    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }

    //region 静态方法

    /**
     * Map对象转换成Map<String, Object>对象
     *
     * @param obj Map对象
     * @return Map<String, Object>对象
     */
    public static Map<String, Object> mapObjectToStringKeyMap(Object obj) {
        if (obj == null) {
            return null;
        }
        else if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            Map<String, Object> resMap = new HashMap<>(map.size());
            map.forEach((k, v) -> resMap.put(k.toString(), v));
            return resMap;
        }
        else {
            return new HashMap<>(0);
        }
    }

    private Object getMapObjectValue(Map<String, Object> map, String key) {
        return getMapObjectValue(map, key, null);
    }

    private Object getMapObjectValue(Map<String, Object> map, String key, Object defValue) {
        return map.getOrDefault(key, defValue);
    }

    public static String getMapStringValue(Map<String, Object> map, String key) {
        return getMapStringValue(map, key, "");
    }

    /**
     * 获取字符串(如果key 不存在返回 “”)
     *
     * @param map map
     * @param key key
     * @return 字符串
     */
    public static String getMapStringValue(Map<String, Object> map, String key, String defValue) {
        if (map.containsKey(key)) {
            Object obj = map.get(key);
            return obj == null ? defValue : obj.toString();
        }
        else {
            return defValue;
        }
    }

    public static Integer getMapIntegerValue(Map<String, Object> map, String key) {
        return getMapIntegerValue(map, key, 0);
    }

    public static Integer getMapIntegerValue(Map<String, Object> map, String key, Integer defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        else {
            String value = obj.toString();
            try {
                return Integer.parseInt(value);
            }
            catch (NumberFormatException ex) {
                return defValue;
            }
        }
    }

    public static Float getMapFloatValue(Map<String, Object> map, String key) {
        return getMapFloatValue(map, key, 0F);
    }

    public static Float getMapFloatValue(Map<String, Object> map, String key, Float defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        else {
            String value = obj.toString();
            try {
                return Float.parseFloat(value);
            }
            catch (NumberFormatException ex) {
                return defValue;
            }
        }
    }

    public static Long getMapLongValue(Map<String, Object> map, String key) {
        return getMapLongValue(map, key, 0L);
    }

    public static Long getMapLongValue(Map<String, Object> map, String key, Long defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        else {
            String value = obj.toString();
            try {
                return Long.parseLong(value);
            }
            catch (NumberFormatException ex) {
                return defValue;
            }
        }
    }

    public static Boolean getMapBooleanValue(Map<String, Object> map, String key) {
        return getMapBooleanValue(map, key, false);
    }

    public static Boolean getMapBooleanValue(Map<String, Object> map, String key, boolean defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        try {
            return Boolean.parseBoolean(obj.toString());
        }
        catch (Exception ex) {
            return defValue;
        }
    }

    /**
     * 将 map 中的 可转化为 int 的数字转化为 int
     *
     * @param resultMap map
     * @return 修正后的map
     */
    public static Map<String, Object> mapNumberToInt(Map<?, ?> resultMap) {
        Map<String, Object> res = new HashMap<>(resultMap.size());
        for (Object keyObj : resultMap.keySet()) {
            String key = keyObj.toString();
            if (resultMap.get(key) instanceof Double) {
                Double value = (Double) resultMap.get(key);
                if (value.intValue() == value) {
                    res.put(key, ((Double) resultMap.get(key)).intValue());
                }
                else {
                    res.put(key, resultMap.get(key));
                }
            }
            else if (resultMap.get(key) instanceof List<?>) {
                res.put(key, listNumberToInt((List<?>) resultMap.get(key)));
            }
            else if (resultMap.get(key) instanceof Map<?, ?>) {
                res.put(key, mapNumberToInt((Map<?, ?>) resultMap.get(key)));
            }
            else {
                res.put(key, resultMap.get(key));
            }
        }
        return res;
    }

    /**
     * 将 list 中的 可转化为 int 的数字转化为 int
     *
     * @param list list
     * @return 修正后的list
     */
    public static List<Object> listNumberToInt(List<?> list) {
        List<Object> res = new ArrayList<>(list.size());
        for (Object o : list) {
            if (o instanceof Number) {
                Double value = (Double) o;
                if (value.intValue() == value) {
                    Object v = value.intValue();
                    res.add(v);
                }
                else {
                    res.add(value);
                }
            }
            else if (o instanceof Map<?, ?>) {
                res.add(mapNumberToInt((Map<?, ?>) o));
            }
            else if (o instanceof List<?>) {
                res.add(listNumberToInt((List<?>) o));
            }
            else {
                res.add(o);
            }
        }
        return res;
    }

    @Deprecated
    public static <T> T mapToObject(Map<String, Object> map, T t) {
        if (map == null) {
            return null;
        }
        try {
            BeanUtils.populate(t, map);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }


    //endregion
    public static Map<String, Object> mapOfJsonString(String jsonString) {
        try {
            Map<String, Object> resultMap = new Gson().fromJson(jsonString, new TypeToken<Map<String, Object>>() {
            }.getType());
            return mapDoubleToInt(resultMap);
        }
        catch (JsonSyntaxException e) {
            throw new RuntimeException("无法解析成Map格式数据");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Map序列化成指定类
     *
     * @param map      map
     * @param classOfT T.class
     * @param <T>      得到的类
     * @return T
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> classOfT) {
        String json = new Gson().toJson(map);
        return new Gson().fromJson(json, classOfT);
    }

    /**
     * 将Map序列化成指定类
     *
     * @param map      map
     * @param classOfT T.class
     * @param <T>      得到的类
     * @return T
     * @author wuyiyuan
     * Created DateTime 2022-01-22 16:42
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> classOfT, GsonAdapter[] typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (GsonAdapter gsonAdapter : typeAdapter) {
            gsonBuilder.registerTypeAdapter(gsonAdapter.type, gsonAdapter.typeAdapter);
        }
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(map);
        return new Gson().fromJson(json, classOfT);
    }

    public static class GsonAdapter {
        Type type;
        Object typeAdapter;

        public GsonAdapter(Type type, Object typeAdapter) {
            this.type = type;
            this.typeAdapter = typeAdapter;
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
                }
                else {
                    res.put(key, resultMap.get(key));
                }
            }
            else if (resultMap.get(key) instanceof List<?>) {
                res.put(key, listDoubleToInt((List<?>) resultMap.get(key)));
            }
            else if (resultMap.get(key) instanceof Map<?, ?>) {
                res.put(key, mapDoubleToInt((Map<?, ?>) resultMap.get(key)));
            }
            else {
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
                }
                else {
                    res.add(value);
                }
            }
            else if (o instanceof Map<?, ?>) {
                res.add(mapDoubleToInt((Map<?, ?>) o));
            }
            else if (o instanceof List<?>) {
                res.add(listDoubleToInt((List<?>) o));
            }
            else {
                res.add(o);
            }
        }
        return res;
    }

    public static MapUtil ofJsonString(String jsonString) {
        Map<String, Object> map = mapOfJsonString(jsonString);
        return new MapUtil(map);
    }

    private final Map<String, Object> map;

    public MapUtil(Map<String, Object> map) {
        this.map = map;
    }

    public String getStringValue(String key) {
        return getMapStringValue(this.map, key);
    }

    public String getStringValue(String key, String defValue) {
        return getMapStringValue(this.map, key, defValue);
    }

    public Integer getIntegerValue(String key) {
        return getMapIntegerValue(map, key);
    }

    public Integer getIntegerValue(String key, Integer defValue) {
        return getMapIntegerValue(map, key, defValue);
    }

    public Float getFloatValue(String key) {
        return getMapFloatValue(map, key);
    }

    public Float getFloatValue(String key, Float defValue) {
        return getMapFloatValue(map, key, defValue);
    }

    public Long getLongValue(String key) {
        return getMapLongValue(map, key);
    }

    public Long getLongValue(String key, Long defValue) {
        return getMapLongValue(map, key, defValue);
    }

    public Object getObjectValue(String key) {
        return getMapObjectValue(map, key);
    }

    public Object getObjectValue(String key, Object defValue) {
        return getMapObjectValue(map, key, defValue);
    }

    public <T> T getValue(String key, T defValue) {
        Object obj = map.getOrDefault(key, defValue);
        if (obj.getClass().equals(defValue.getClass())) {
            return (T) obj;
        }
        else if (throwException) {
            throw new RuntimeException("类型转换失败。");
        }
        else {
            return defValue;
        }
    }

    public Boolean getBooleanValue(String key) {
        return getMapBooleanValue(map, key);
    }

    public Boolean getBooleanValue(String key, boolean defValue) {
        return getMapBooleanValue(map, key, defValue);
    }

    public Timestamp getTimestampValue(String key) {
        return getTimestampValue(key, Timestamp.valueOf(LocalDateTime.now()));
    }

    public Timestamp getTimestampValue(String key, Timestamp defValue) {
        Object obj = map.get(key);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Timestamp) {
            return (Timestamp) obj;
        }
        else if (obj instanceof Number) {
            return new Timestamp(((Number) obj).longValue());
        }
        else if (obj instanceof String) {
            try {
                return Timestamp.valueOf((String) obj);
            }
            catch (Exception ex) {
                return defValue;
            }
        }
        else {
            return defValue;
        }
    }

    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, BigDecimal.ZERO);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        Object obj = map.get(key);
        if (obj == null) {
            return defValue;
        }
        else if (obj instanceof Double) {
            return BigDecimal.valueOf((Double) obj);
        }
        else if (obj instanceof Long) {
            return BigDecimal.valueOf((Long) obj);
        }
        else if (obj instanceof Number) {
            return BigDecimal.valueOf(((Number) obj).doubleValue());
        }
        else if (obj instanceof String) {
            try {
                return new BigDecimal((String) obj);
            }
            catch (Exception ex) {
                return defValue;
            }
        }
        else {
            return defValue;
        }
    }

    public <T> T getValue(String key, Function<Object, T> function) {
        return function.apply(map.getOrDefault(key, null));
    }

    public <T> List<T> getListValue(String key, Function<Object, T> function) {
        Function<Object, ResInfo<T>> function2 = (obj) -> new ResInfo<>(true, function.apply(obj));
        return getCollectionValueAllowJudgment(key, function2, () -> new ArrayList<>(0));
    }

    /**
     * Collection获取(List;Set)
     *
     * @param key                  键值
     * @param function             转换
     * @param initializeCollection 初始化 Collection
     * @return java.util.Collection<T>
     */
    public <T, R extends Collection<T>> R getCollectionValue(String key, Function<Object, T> function, Supplier<R> initializeCollection) {
        Function<Object, ResInfo<T>> function2 = (obj) -> new ResInfo<>(true, function.apply(obj));
        return getCollectionValueAllowJudgment(key, function2, initializeCollection);
    }

    /**
     * Collection获取(List;Set)
     *
     * @param key                  键值
     * @param function             转换
     * @param initializeCollection 初始化 Collection
     * @return java.util.Collection<T>
     */
    public <T, R extends Collection<T>> R getCollectionValueAllowJudgment(String key, Function<Object, ResInfo<T>> function, Supplier<R> initializeCollection) {
        R resList = initializeCollection.get();
        if (this.map.containsKey(key)) {
            Object obj = this.map.get(key);
            if (obj instanceof Collection<?>) {
                Collection<?> list = (Collection<?>) obj;
                list.forEach(item -> {
                    ResInfo<T> resInfo = function.apply(item);
                    if (resInfo.res) {
                        resList.add(resInfo.resData);
                    }
                });
                return resList;
            }
            else if (this.throwException) {
                throw new RuntimeException("无法解析非List数据");
            }
            else {
                return resList;
            }
        }
        else if (this.throwException) {
            throw new RuntimeException("不存在的键值。");
        }
        else {
            return resList;
        }
    }

    public Map<String, Object> getMap(String key) {
        return getMap(key, new HashMap<>(0));
    }

    public Map<String, Object> getMap(String key, Map<String, Object> defValue) {
        Object obj = map.get(key);
        if (obj == null) {
            return defValue;
        }
        else {
            try {
                return mapObjectToStringKeyMap(obj);
            }
            catch (Exception ignored) {
                return defValue;
            }
        }
    }

    public <T> PageImpl<T> getPageImpl(String key, MapToT<T> mapToT) {
        PageImpl<T> def = new PageImpl<>(new ArrayList<>(0));
        return getPageImpl(key, mapToT, def);
    }

    public <T> PageImpl<T> getPageImpl(String key, MapToT<T> mapToT, PageImpl<T> defValue) {
        Object obj = map.get(key);
        if (obj == null) {
            return defValue;
        }
        else {
            try {
                Map<String, Object> map = mapObjectToStringKeyMap(obj);
                return mapToPageImpl(map, mapToT);
            }
            catch (Exception ignored) {
                return defValue;
            }
        }
    }

    /**
     * 获取ResultMap模型中的分页数据
     *
     * @param map    原始map数据
     * @param mapToT 转换函数
     * @param <T>    泛型
     * @return org.springframework.data.domain.PageImpl<T>
     */
    public static <T> @NotNull PageImpl<T> mapToPageImpl(Map<String, Object> map, MapToT<T> mapToT) {
        MapUtil mapUtil = new MapUtil(map);
        int number = mapUtil.getIntegerValue("number", 0);
        int size = mapUtil.getIntegerValue("size", 0);
        PageRequest pageable = PageRequest.of(number, size);
        long totalElements = mapUtil.getLongValue("totalElements", 0L);
        List<T> list = mapUtil.getListValue("content", obj -> mapToT.mapToT(MapUtil.mapObjectToStringKeyMap(obj)));
        return new PageImpl<>(list, pageable, totalElements);
    }

    public static class ResInfo<T> {
        Boolean res;
        T resData;

        public ResInfo(Boolean res, T resData) {
            this.res = res;
            this.resData = resData;
        }
    }

    public static <T> T cloneObj(T a, Class<T> classOfT) {
        String json = new Gson().toJson(a);
        return new Gson().fromJson(json, classOfT);
    }
}

