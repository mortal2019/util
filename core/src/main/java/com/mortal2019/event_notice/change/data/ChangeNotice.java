package com.mortal2019.event_notice.change;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 更新通知
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:48
 */
public class ChangeNotice<T> {
    private final Object sender;
    final ExecutorService executorService;
    private final Map<String, IChangeNotice<T>> noticeMap = new HashMap<>(0);

    public ChangeNotice(Object sender) {
        this.sender = sender;
        this.executorService = ThreadPoolExecutorBuilder.create().builder();
    }

    /**
     * 注册
     *
     * @param iChangeNotice 注册的对象
     */
    public void reg(IChangeNotice<T> iChangeNotice) {
        this.noticeMap.put(iChangeNotice.getKey(), iChangeNotice);
    }

    /**
     * 注销通知
     *
     * @param iChangeNotice 注销的对象
     */
    public void unReg(IChangeNotice<T> iChangeNotice) {
        this.noticeMap.remove(iChangeNotice.getKey());
    }

    /**
     * 数据更新通知
     *
     * @param data        变更的数据
     * @param dataStatus  变更数据的状态
     * @param changeModel 更新模式
     */
    public void changeNotice(T data, ChangeData.DataStatus dataStatus, String changeModel) {
        this.noticeMap.forEach((key, item) -> IgnoredException.run(() -> this.executorService.execute(() -> item.changeNotice(sender, new ChangeData<>(data, dataStatus, changeModel)))));
    }

    /**
     * 数据更新通知
     *
     * @param data       变更的数据
     * @param dataStatus 变更数据的状态
     */
    public void changeNotice(T data, ChangeData.DataStatus dataStatus) {
        this.changeNotice(data, dataStatus, null);
    }

    /**
     * 数据更新通知
     *
     * @param data 变更的数据
     */
    public void changeNotice(T data) {
        this.changeNotice(data, null, null);
    }
}
