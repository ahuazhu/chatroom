package io.isharon.chat.handler;

import io.isharon.chat.assets.ChatRoom;
import io.isharon.chat.assets.RoomManager;
import io.isharon.chat.assets.User;
import io.isharon.chat.assets.UserChannelManager;
import io.isharon.chat.model.Message;
import io.isharon.chat.pool.Pool;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public abstract class DefaultProcessor implements Processor {

    public void process(final ChannelHandlerContext ctx, final ChannelEvent e, final Message message) {
        Pool.submit(new Callable() {
            public Object call() throws Exception {
                doProcess(ctx, e, message);
                return null;
            }
        });
    }

    public void broadCast(Message message) {
        //简单起见，只有一个房间
        ChatRoom chatRoom = RoomManager.getRoomList().get(0);
        List<User> userList = chatRoom.getUsers();

        //广播
        for (User user : userList) {
            UserChannelManager.getChannel(user).write(message.toString());
        }
    }

    protected Message systemMessage(String content, Message.Type type) {
        Message message = new Message();
        message.setDate(new Date());

        message.setContent(content);
        message.setUserId(0);
        message.setUserName("System");
        message.setType(type);
        return message;
    }

    protected void sendToUser(User user, Message message) {
        UserChannelManager.getChannel(user).write(message.toString());
    }

    protected void sendToChannel(Channel channel, Message message) {
        channel.write(message.toString());
    }



    protected abstract void doProcess(ChannelHandlerContext ctx, ChannelEvent e, Message message);
}
