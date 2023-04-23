package com.mortal2019.event_notice.change.impl;

import com.mortal2019.event_notice.change.IChangeNotice;
import com.mortal2019.event_notice.change.data.ChangeData;
import com.mortal2019.event_notice.change.data.Data;

import java.util.Vector;
import java.util.function.Consumer;

/**
 * 线程安全消费事件
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:54
 */
public class ChangeNoticeVectorImpl<T> implements IChangeNotice<T> {
    private final String key;
    private final Consumer<Data<T>> consumer;
    Vector<Data<T>> vector;

    public ChangeNoticeVectorImpl(String key, Consumer<Data<T>> consumer) {
        this.vector = new Vector<>(0);
        this.key = key;
        this.consumer = consumer;
    }

    private void run() {
        synchronized (this) {
            while (this.vector.size() > 0) {
                this.consumer.accept(this.vector.get(0));
                this.vector.remove(0);
            }
        }
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void changeNotice(Object sender, ChangeData<T> changeData) {
        this.vector.add(new Data<>(sender, changeData));
        this.run();
    }
}
