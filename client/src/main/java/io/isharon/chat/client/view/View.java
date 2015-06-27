package io.isharon.chat.client.view;

import io.isharon.chat.model.Message;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public interface View {

    void onMessage(Message message);

    void setChannel(Channel channelFuture);

    void start();
}
