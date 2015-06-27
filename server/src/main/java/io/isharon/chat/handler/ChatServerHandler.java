package io.isharon.chat.handler;

import io.isharon.chat.assets.RoomManager;
import io.isharon.chat.assets.UserChannelManager;
import io.isharon.chat.assets.ChatRoom;
import io.isharon.chat.model.Message;
import io.isharon.chat.assets.User;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import java.util.Date;
import java.util.List;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ChatServerHandler extends SimpleChannelHandler {
    public static final ChannelGroup channelGroup = new DefaultChannelGroup("server-channel-group");


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

        if (message.getType() == Message.Type.CHAT) {
            castMessage(content);

        } else if (message.getType() == Message.Type.REGISTER) {
            try {
                User user = User.newUser(message.getContent());
                acceptName(e, user);

            } catch (RuntimeException re) {
                rejectName(ctx, e, message);
            }
        }

        System.out.println(content);
        if (content.equalsIgnoreCase("bye")) {
            e.getChannel().close();
            channelGroup.remove(e.getChannel());
            return;
        }
    }

    private void acceptName(MessageEvent e, User user) {
        UserChannelManager.addUser(user, e.getChannel());
        RoomManager.getRoomList().get(0).addUser(user); //简单起见，默认放到第一个聊天室

        Message accept = systemMessage(user.getUserName()+":"+user.getUserId(), Message.Type.NAME_ACCEPT);

        e.getChannel().write(accept.toString());

        Message welcome = systemMessage("Welcome " + user.getUserName(), Message.Type.CHAT);
        castMessage(welcome.toString());
    }

    private String nameIdPair(User user) {
        return user.getUserName()+":"+user.getUserId();
    }

    private void rejectName(ChannelHandlerContext ctx, MessageEvent e, Message message) {

        Message reject = systemMessage("Your name exist, pls retry another", Message.Type.NAME_REJECT);
        e.getChannel().write(reject.toString());
    }

    private void castMessage(String content) {
        //简单起见，只有一个房间
        ChatRoom chatRoom = RoomManager.getRoomList().get(0);
        List<User> userList = chatRoom.getUsers();

        //广播
        for (User user : userList) {
            UserChannelManager.getChannel(user).write(content);
        }
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        System.out.println("new Connected" + e.getChannel() + ctx.getHandler().getClass().getCanonicalName());
        channelGroup.add(e.getChannel());

        Message message = systemMessage("Welcome, please enter your nickname", Message.Type.WELCOME);

        e.getChannel().write(message.toString());

    }

    private Message systemMessage(String content, Message.Type type) {
        Message message = new Message();
        message.setDate(new Date());

        message.setContent(content);
        message.setUserId(0);
        message.setUserName("System");
        message.setType(type);
        return message;
    }

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }
}

