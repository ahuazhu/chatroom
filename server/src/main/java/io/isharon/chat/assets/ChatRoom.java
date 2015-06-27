package io.isharon.chat.assets;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ChatRoom {

    public ChatRoom(int roomId, String roomName) {
        this.roomId = roomId;
        this.name = roomName;

        this.users = new LinkedList<User>();
    }

    private String name;

    private int roomId;

    private List<User> users;

    public void addUser(User user) {
        synchronized (users) {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        if (user != null) removeUser(user.getUserId());
    }

    public void removeUser(int userId) {
        synchronized (users) {
            for (User user : users) {
                if (user.getUserId() == userId) {
                    users.remove(user);
                }
            }
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public final List<User> getUsers() {
        return users;
    }

}
