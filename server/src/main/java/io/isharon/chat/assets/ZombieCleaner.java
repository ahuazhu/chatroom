package io.isharon.chat.assets;

import io.isharon.chat.handler.ChatServerHandler;
import io.isharon.chat.model.Message;
import io.isharon.chat.pool.Pool;
import org.jboss.netty.channel.Channel;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ZombieCleaner {

    private List<ChatRoom> watchList = new LinkedList<ChatRoom>();

    public void watch(ChatRoom room) {
        watchList.add(room);
    }

    public boolean shouldBeKickOut(User user) {
        System.out.println(System.currentTimeMillis());
        System.out.println(user.getLastActivityTime());
        return System.currentTimeMillis() - user.getLastActivityTime() > 1000 * 60;
    }

    void go() {
        Pool.submit(new Callable<Object>() {
            public Object call() throws Exception {
                for (; ; ) {
                    _go();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        });

    }

    void _go() {
        for (ChatRoom chatRoom : watchList) {
            List<User> users = chatRoom.getUsers();
            for (User user : users) {
                if (shouldBeKickOut(user)) {
                    kickoutUser(chatRoom, user);
                }
            }
        }
    }

    private void kickoutUser(ChatRoom room, User user) {


        Message message = new Message();
        message.setDate(new Date());
        message.setUserName("system");
        message.setUserId(0);
        message.setType(Message.Type.CHAT);
        message.setContent("Bye " + user.getUserName());

        for (User _user : room.getUsers()) {
            sendMsgToUser(_user, message);
        }

        message.setType(Message.Type.BYE);
        sendMsgToUser(user, message);

        Channel channel = UserChannelManager.getChannel(user);

        if (ChatServerHandler.channelGroup.contains(channel)) {
            ChatServerHandler.channelGroup.remove(channel);
        }
        channel.close();
        System.err.println("Bye, " + user.getUserName());
        User.removeUser(user.getUserName());
        room.removeUser(user);
    }

    private void sendMsgToUser(User user, Message message) {
        Channel channel = UserChannelManager.getChannel(user);
        channel.write(message.toString());
    }


}
