package io.isharon.chat.handler;

import io.isharon.chat.model.Message;
import io.isharon.chat.assets.User;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ChatServerHandler extends SimpleChannelHandler {
    public static final ChannelGroup channelGroup = new DefaultChannelGroup("server-channel-group");


    private Processor chatHandler = new ChartProcessor();

    private Processor registerHandler = new RegisterHandler();

    private Processor welcomeProcessor = new WelcomeProcessor();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception {
        e.getCause().printStackTrace();
        Channel channel = e.getChannel();
        channel.close();
        if (channelGroup.contains(channel)) {
            channelGroup.remove(channel);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        String content = (String) e.getMessage();
        Message message = Message.fromString(content);

        User user = User.findUser(message.getUserName());

        if (user != null) {
            user.setLastActivityTime(System.currentTimeMillis());
        }

        if (message.getType() == Message.Type.CHAT) {
            chatHandler.process(ctx, e, message);

        } else if (message.getType() == Message.Type.REGISTER) {
            registerHandler.process(ctx, e, message);
        }

        System.out.println(content);
    }


    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {

        welcomeProcessor.process(ctx, e, null);
    }

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }
}

