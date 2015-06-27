package io.isharon.chat.handler;

import io.isharon.chat.assets.ChatRoom;
import io.isharon.chat.assets.RoomManager;
import io.isharon.chat.assets.User;
import io.isharon.chat.assets.UserChannelManager;
import io.isharon.chat.model.Message;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ByeProcessor extends DefaultProcessor {

    @Override
    protected void doProcess(ChannelHandlerContext ctx, ChannelEvent e, Message msg) {
        User user = User.findUser(msg.getUserName());
        ChatRoom room = RoomManager.getRoomList().get(0);

        Message message = new Message();
        message.setDate(new Date());
        message.setUserName("system");
        message.setUserId(0);
        message.setType(Message.Type.CHAT);
        message.setContent("Bye " + user.getUserName());

        for (User _user : room.getUsers()) {
            sendToUser(_user, message);
        }

        message.setType(Message.Type.BYE);
        sendToUser(user, message);

        Channel channel = UserChannelManager.getChannel(user);

        if (ChatServerHandler.channelGroup.contains(channel)) {
            ChatServerHandler.channelGroup.remove(channel);
        }
        channel.close();
        System.err.println("Bye, " + user.getUserName());
        User.removeUser(user.getUserName());
        room.removeUser(user);
    }
}
