package io.isharon.chat.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.isharon.chat.util.ObjectMapperFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class Message {

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static enum Type{
        WELCOME,

        REGISTER,

        NAME_ACCEPT,

        NAME_REJECT,

        CHAT,
    }

    private Type type;

    private Date date;

    private String userName;

    private int userId;


    private String content;


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Message fromString(String content) {

        try {
            return ObjectMapperFactory.getMapper().readValue(content, Message.class);
        } catch (IOException e) {
            return null;
        }
    }

    public String toString() {
        try {
            return ObjectMapperFactory.getMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
