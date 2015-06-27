package io.isharon.chat.client;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class Status {

    public static final int WAITING_NAME = 1;

    public static final int NAME_SENT = 2;

    public static final int NAME_ACCEPT = 3;



    private static Status instance = new Status();


    private volatile int status;
    private String name;
    private int userId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static Status getInstance() {
        return instance;
    }

    private Status() {

        this.status = WAITING_NAME;
    }

    public void sentName() {
        this.status = NAME_SENT;
    }

    public void resetName() {
        this.status = WAITING_NAME;
    }

    public void acceptName(String name, int userId) {
        this.status = NAME_ACCEPT;
        this.name = name;
        this.userId = userId;
    }


    public int getStatus() {
        return status;
    }

}
