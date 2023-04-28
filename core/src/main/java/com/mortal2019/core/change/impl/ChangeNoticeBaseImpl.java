package com.mortal2019.core.change.impl;

import com.mortal2019.core.change.IChangeNotice;
import com.mortal2019.core.change.data.ChangeData;
import com.mortal2019.core.change.data.Data;

import java.util.function.Consumer;

/**
 * 消费者使用示例: <br/>
 * <pre>
 * {@code
 * public class Consumer {
 *   private final  ChangeNoticeBaseImpl<T> changeNoticeBase;
 *
 *   public Consumer() {
 *    changeNoticeBase=new ChangeNoticeBaseImpl<>(this.getClass().getName(),this::execute);
 *   }
 *
 *   // 注册到发送者的Map中
 *   @Autowired
 *   public void reg(Sender sender) {
 *     sender.reg(changeNoticeBase);
 *   }
 *
 *   public void execute(ChangeNoticeBaseImpl.Data<T> data) {
 *        // 处理发送者传送的消息
 *   }
 * }
 * }
 * </pre>
 *
 * 线程不安全消费者
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
