package io.isharon.chat.handler;

import io.isharon.chat.assets.RoomManager;
import io.isharon.chat.assets.User;
import io.isharon.chat.assets.UserChannelManager;
import io.isharon.chat.model.Message;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class RegisterHandler extends DefaultProcessor {

    @Override
    protected void doProcess(ChannelHandlerContext ctx, ChannelEvent e, Message message) {
        try {
            User user = User.newUser(message.getContent());
            acceptName(e, user);

        } catch (RuntimeException re) {
            rejectName(ctx, e, message);
        }

    }

    private void acceptName(ChannelEvent e, User user) {
        UserChannelManager.addUser(user, e.getChannel());
        RoomManager.getRoomList().get(0).addUser(user); //简单起见，默认放到第一个聊天室

        Message accept = systemMessage(nameIdPair(user), Message.Type.NAME_ACCEPT);

        e.getChannel().write(accept.toString());

        Message welcome = systemMessage("Welcome " + user.getUserName(), Message.Type.CHAT);
        broadCast(welcome);
    }


    private String nameIdPair(User user) {
        return user.getUserName()+":"+user.getUserId();
    }

    private void rejectName(ChannelHandlerContext ctx, ChannelEvent e, Message message) {
        Message reject = systemMessage("Your name exist, pls retry another", Message.Type.NAME_REJECT);
        sendToChannel(e.getChannel(), reject);
    }




}
