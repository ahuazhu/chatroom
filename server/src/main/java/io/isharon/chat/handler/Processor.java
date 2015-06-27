package io.isharon.chat.handler;

import io.isharon.chat.model.Message;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import java.nio.channels.Channel;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public interface Processor {
    void process(ChannelHandlerContext ctx, ChannelEvent e, Message message);
}
