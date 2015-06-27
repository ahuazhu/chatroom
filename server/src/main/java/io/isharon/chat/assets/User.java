package io.isharon.chat.assets;


import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class User {

    //todo using DB/redis
    private static int _id  = 0;

    //todo using DB/redis
    private static Map<String, User> _users = new HashMap<String, User>();


    private int userId;

    private String userName;

    private long lastActivityTime;

    public static User findUser(String name) {
        return _users.get(name);
    }

    public static User newUser(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name should not be blank");
        }
        if (_users.containsKey(name)) {
            throw new RuntimeException("user with username " + name + " already exist");
        }

        return new User(name);
    }

    public static void removeUser(String name) {
        _users.remove(name);
    }

    private User(String name) {
        synchronized (User.class) {
            this.userId = _id ++;
            this.userName = name;
            this.lastActivityTime = System.currentTimeMillis();
            _users.put(name, this);
        }
    }


    public long getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(long lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
