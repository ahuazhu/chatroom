package io.isharon.chat.client.view;

import io.isharon.chat.client.Status;
import io.isharon.chat.model.Message;
import org.jboss.netty.channel.Channel;

import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ConsoleView implements View {


    private Channel channel;

    private static final DateFormat DFT = new java.text.SimpleDateFormat("HH:mm");

    public ConsoleView() {

    }

    public void start() {
        new Runnable() {
            public void run() {
                sendMessage();
            }
        }.run();
    }



    public void onMessage(Message message) {

        Status status = Status.getInstance();

        String msg = null;

        switch (message.getType()) {
            case NAME_ACCEPT:
                String[] nameId = message.getContent().split(":");
                String name = nameId[0];
                int userId = Integer.parseInt(nameId[1]);
                status.acceptName(name, userId);
                break;
            case NAME_REJECT:
                status.resetName();
                break;
        }

        msg = message.getContent();

        if (message != null) {
            System.out.println(String.format("%s %s:%s",
                    DFT.format(message.getDate()), message.getUserName(), msg));
        }
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void sendMessage() {
        Scanner scanner = new Scanner(System.in);
        for ( ; ; ) {
            if (scanner.hasNext()) {
                final String input = scanner.next();
                Message message = new Message();

                switch (Status.getInstance().getStatus()) {
                    case Status.WAITING_NAME:
                    case Status.NAME_SENT:
                        message.setType(Message.Type.REGISTER);
                        break;
                    case Status.NAME_ACCEPT:
                        message.setType(Message.Type.CHAT);
                        break;
                }

                message.setContent(input);
                message.setDate(new Date());

                message.setUserId(Status.getInstance().getUserId());
                message.setUserName(Status.getInstance().getName());

                if (channel != null) {
                    channel.write(message.toString());

                }

            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
