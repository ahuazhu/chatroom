package io.isharon.chat.handler;

import io.isharon.chat.model.Message;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class WelcomeProcessor extends DefaultProcessor {

    @Override
    protected void doProcess(ChannelHandlerContext ctx, ChannelEvent e, Message message) {
        System.out.println("new Connected" + e.getChannel() + ctx.getHandler().getClass().getCanonicalName());
        ChatServerHandler.getChannelGroup().add(e.getChannel());

        Message message2 = systemMessage("Welcome, please enter your nickname", Message.Type.WELCOME);

        sendToChannel(e.getChannel(), message2);
    }
}
