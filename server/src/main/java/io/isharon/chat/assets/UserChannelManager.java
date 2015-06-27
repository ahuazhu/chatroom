package io.isharon.chat.assets;

import org.jboss.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class UserChannelManager {

    private static final Map<User, Channel> userChannel = new ConcurrentHashMap();

    public static void addUser(User user, Channel channel) {
        userChannel.put(user, channel);
    }

    public static Channel getChannel(User user) {
        return userChannel.get(user);
    }
}
