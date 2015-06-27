package io.isharon.chat.assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class RoomManager {

    private static List<ChatRoom> roomList = new ArrayList<ChatRoom>();

    private static ZombieCleaner cleaner = new ZombieCleaner();

    static {
        //简单起见，只创建一个房间

        ChatRoom chatRoom = new ChatRoom(1, "聊天室");
        addRoom(chatRoom);

        cleaner.go();
    }

    public static void addRoom(ChatRoom chatRoom) {
        roomList.add(chatRoom);
        cleaner.watch(chatRoom);
    }

    public static List<ChatRoom> getRoomList() {
        return new ArrayList<ChatRoom>(roomList);
    }


}
