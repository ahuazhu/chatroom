package io.isharon.chat.client.handler;

/**
 * Created by zhengwenzhu on 15-6-27.
 */

import io.isharon.chat.client.view.View;
import io.isharon.chat.model.Message;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class ChatClientHandler extends SimpleChannelHandler {
    public static final ChannelGroup channelGroup = new DefaultChannelGroup(
            "client-channel-group");

    View view;

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
        if (channelGroup.contains(e.getChannel())) {
            channelGroup.remove(e.getChannel());
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        String content = (String) e.getMessage();
        Message message = Message.fromString(content);

        if (view != null) {
            view.onMessage(message);
        }


        if (content.equalsIgnoreCase("bye")) {
            synchronized (this) {
                this.notify();
            }
            return;
        }

    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        channelGroup.add(e.getChannel());
    }
}
