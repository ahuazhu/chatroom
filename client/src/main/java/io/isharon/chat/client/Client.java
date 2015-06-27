package io.isharon.chat.client;

/**
 * Created by zhengwenzhu on 15-6-27.
 */

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;

import io.isharon.chat.client.handler.ChatClientHandler;
import io.isharon.chat.client.view.ConsoleView;
import io.isharon.chat.client.view.View;
import javafx.animation.Animation;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class Client {

    ClientBootstrap bootstrap;
    ChatClientHandler handler;
    ChannelFuture channelFuture;
    private View view = new ConsoleView();

    public void init() {
        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        handler = new ChatClientHandler();
        handler.setView(view);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();
                channelPipeline.addLast("encode", new StringEncoder());
                channelPipeline.addLast("decode", new StringDecoder());
                channelPipeline.addLast("handler", handler);
                return channelPipeline;
            }
        });
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("reuseAddress", true);
    }

    public void start() {
        channelFuture = bootstrap.connect(new InetSocketAddress(7101));

        view.setChannel(channelFuture.getChannel());

        view.start();

    }

    public void stop() {
        channelFuture.awaitUninterruptibly();
        if (!channelFuture.isSuccess()) {
            channelFuture.getCause().printStackTrace();
        }
        channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }

    public ChatClientHandler getHandler() {
        return handler;
    }

    public static void main(String[] args) {
        Client chatClient = new Client();
        chatClient.init();
        chatClient.start();
        synchronized (chatClient.getHandler()) {
            try {
                chatClient.getHandler().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chatClient.stop();
    }
}

