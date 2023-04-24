package com.mortal2019.core.change.impl;

import com.mortal2019.core.change.IChangeNotice;
import com.mortal2019.core.change.data.ChangeData;
import com.mortal2019.core.change.data.Data;

import java.util.function.Consumer;

/**
 * 线程不安全消费事件
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:55
 */
public class ChangeNoticeBaseImpl<T> implements IChangeNotice<T> {
    private final String key;
    private final Consumer<Data<T>> consumer;

    public ChangeNoticeBaseImpl(String key, Consumer<Data<T>> consumer) {
        this.key = key;
        this.consumer = consumer;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void changeNotice(Object sender, ChangeData<T> changeData) {
        if (this.consumer != null) {
            this.consumer.accept(new Data<>(sender, changeData));
        }
    }
}
