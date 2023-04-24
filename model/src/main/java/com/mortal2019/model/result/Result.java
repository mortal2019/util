package com.mortal2019.model.result;

import com.mortal2019.core.asserts.BaseAssert;
import com.mortal2019.core.functional.MapToT;
import com.mortal2019.core.functional.ObjectToT;
import com.mortal2019.model.exception.ResultException;
import com.mortal2019.util.map.MapUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 14:26
 */
public class Result<T> implements Serializable {
    public static final int OK = 0;
    public static final int ERROR = -1;

    /**
     * 返回码
     */
    private Integer flag;
    /**
     * 信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public Result() {
        this.flag = OK;
        this.msg = "操作成功";
        this.data = null;
    }

    private Result(T data) {
        this();
        this.data = data;
    }

    private Result(T data, Integer flag) {
        this();
        this.data = data;
        this.flag = flag;
    }

    private Result(T data, Integer flag, String msg) {
        this();
        this.data = data;
        this.flag = flag;
        this.msg = msg;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.flag.equals(OK);
    }

    /**
     * 通过静态方法获取实例
     */
    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull Result<T> of(T data) {
        return new Result<>(data);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T> @NotNull Result<T> of(T data, Integer flag) {
        return new Result<>(data, flag);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static <T> @NotNull Result<T> of(T data, Integer flag, String msg) {
        return new Result<>(data, flag, msg);
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull Result<T> error(String msg) {
        return new Result<>(null, ERROR, msg);
    }

    @Contract(value = "_, _-> new", pure = true)
    public static <T> @NotNull Result<T> error(String msg, int errorCode) {
        return new Result<>(null, errorCode, msg);
    }

    @Contract(value = " -> new", pure = true)
    public static <T> @NotNull Result<T> success() {
        return new Result<>(null, OK, "");
    }

    private static final String FLAG_KEY = "flag";
    private static final String MSG_KEY = "msg";
    private static final String DATA_KEY = "data";

    @Deprecated
    public static <T> @NotNull Result<T> ofMap(Map<String, Object> map, Class<T> clazz) {
        Result<T> result = new Result<>();
        if (map.containsKey(FLAG_KEY)) {
            result.flag = ((Number) map.get(FLAG_KEY)).intValue();
        }
        else {
            throw new RuntimeException("缺少关键字" + FLAG_KEY);
        }
        if (map.containsKey(MSG_KEY)) {
            result.msg = map.get(MSG_KEY).toString();
        }
        else {
            throw new RuntimeException("缺少关键字" + MSG_KEY);
        }
        if (map.containsKey(DATA_KEY)) {
            if (clazz.isInstance(map.get(DATA_KEY))) {
                result.data = clazz.cast(map.get(DATA_KEY));
            }
            else {
                throw new RuntimeException("Map 关键字‘" + DATA_KEY + "’无法转换为当前类型。");
            }
        }
        else {
            throw new RuntimeException("缺少关键字" + DATA_KEY);
        }
        return result;
    }

    /**
     * 获取ResultMap模型中的数据
     *
     * @param map 传入数据
     * @return java.lang.Object
     */
    public static Object getResultObj(Map<String, Object> map) {
        return getValueOfResultMap(map, o -> o);
    }

    /**
     * 获取ResultMap模型中的数据
     *
     * @param map    传入数据
     * @param mapToT 转换函数
     * @param <T>    转换类型
     * @return java.util.List<T> 返回转换后的数组（过滤null数据）
     */
    public static <T> List<T> getResultToList(Map<String, Object> map, MapToT<T> mapToT) {
        BaseAssert.assertNotNull(mapToT, new ResultException("不能传入null解析方法mapToT。"));
        BaseAssert.assertNotNull(map, new ResultException("传入null值,无法进行解析map。"));
        ArrayList<?> arrayList = getValueOfResultMap(map, o -> {
            BaseAssert.assertTrue(o instanceof ArrayList, new ResultException("对象无法解析成列表"));
            return (ArrayList<?>) o;
        });
        List<T> list = new ArrayList<>(arrayList.size());
        for (Object obj : arrayList) {
            Map<String, Object> itemMap = MapUtil.mapObjectToStringKeyMap(obj);
            final T t = mapToT.mapToT(itemMap);
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 获取ResultMap模型中的数据
     *
     * @param map    传入数据
     * @param mapToT 转换函数
     * @param <T>    转换类型
     * @return T 返回转换后的结果
     */
    public static <T> T getResultData(Map<String, Object> map, MapToT<T> mapToT) {
        BaseAssert.assertNotNull(mapToT, new ResultException("不能传入null解析方法mapToT。"));
        Object obj = getValueOfResultMap(map, o -> o);
        Map<String, Object> itemMap = MapUtil.mapObjectToStringKeyMap(obj);
        return mapToT.mapToT(itemMap);
    }

    /**
     * 将map转换成Result
     *
     * @param map Result的map结构
     * @return  com.mortal2019.model.result.Result<java.lang.Object> 返回Result结构
     */
    public static Result<Object> getResult(Map<String, Object> map) {
        BaseAssert.assertNotNull(map, new ResultException("传入null值,无法进行解析map。"));
        Result<Object> result = new Result<>();
        BaseAssert.assertTrue(map.containsKey(FLAG_KEY), new ResultException("flag信息有效判定字段不存在。"));
        result.setFlag((int) map.get(FLAG_KEY));
        if (map.containsKey(MSG_KEY)) {
            result.setMsg((String)map.get(MSG_KEY));
        }
        if (map.containsKey(DATA_KEY)) {
            result.setData(map.get(DATA_KEY));
        }
        return result;
    }

    /**
     * 获取ResultMap的数据
     *
     * @param map       Result Map形式
     * @param objectToT 数据转换函数
     * @param <T>       转换后的类型
     * @return T 转换后的结果
     */
    public static <T> T getValueOfResultMap(Map<String, Object> map, ObjectToT<T> objectToT) {
        BaseAssert.assertNotNull(objectToT, new ResultException("不能传入null解析方法mapToT。"));
        BaseAssert.assertNotNull(map, new ResultException("传入null值,无法进行解析map。"));
        Result<Object> result = getResult(map);
        BaseAssert.assertTrue(result.isSuccess(), result.getMsg());
        return objectToT.objectToT(result.getData());
    }

    /**
     * 获取ResultMap模型中的分页数据
     *
     * @param map    原始map数据
     * @param mapToT 转换函数
     * @param <T>    泛型
     * @return org.springframework.data.domain.PageImpl<T>
     */
    @Contract("_, _ -> new")
    public static <T> @NotNull PageImpl<T> getResultToPageImpl(Map<String, Object> map, MapToT<T> mapToT) {
        BaseAssert.assertNotNull(mapToT, new ResultException("不能传入null解析方法mapToT。"));
        Result<Object> result = getResult(map);
        if (result.isSuccess()) {
            MapUtil mapUtil = new MapUtil(MapUtil.mapObjectToStringKeyMap(result.getData()));
            int number = mapUtil.getIntegerValue("number", 0);
            int size = mapUtil.getIntegerValue("size", 0);
            PageRequest pageable = PageRequest.of(number, size);
            long totalElements = mapUtil.getLongValue("totalElements", 0L);
            List<T> list = mapUtil.getListValue("content", obj -> mapToT.mapToT(MapUtil.mapObjectToStringKeyMap(obj)));
            return new PageImpl<>(list, pageable, totalElements);
        }
        else {
            throw new RuntimeException(result.getMsg());
        }
    }
}
