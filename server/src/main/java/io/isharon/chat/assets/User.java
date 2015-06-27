package io.isharon.chat.assets;


import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class User {

    //todo using DB/redis
    private static int _id  = 0;

    //todo using DB/redis
    private static Set<String> _names = new HashSet<String>();


    public static User newUser(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name should not be blank");
        }
        if (_names.contains(name)) {
            throw new RuntimeException("user with username " + name + " already exist");
        }

        return new User(name);
    }

    public static void removeUser(String name) {
        _names.remove(name);
    }

    private User(String name) {
        synchronized (User.class) {
            this.userId = _id ++;
            this.userName = name;
            _names.add(name);
        }
    }



    private int userId;

    private String userName;

    public long getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(long lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    private long lastActivityTime;

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
