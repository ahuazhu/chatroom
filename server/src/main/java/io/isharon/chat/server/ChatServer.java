package io.isharon.chat.server;

import io.isharon.chat.handler.ChatServerHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ChatServer {
    public static final int PORT = 7101;

    private ServerBootstrap bootstrap;

    private ChannelGroup channelGroup;


    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.init();
        chatServer.startup();
    }

    public void init() {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));

        final ChatServerHandler handler = new ChatServerHandler();
        channelGroup = handler.getChannelGroup();

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encode", new StringEncoder());
                pipeline.addLast("decode", new StringDecoder());
                pipeline.addLast("handler", handler);
                return pipeline;
            }
        });
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.setOption("reuseAddress", true);
    }
    public void startup() {
        channelGroup.add(bootstrap.bind(new InetSocketAddress(PORT)));
    }
    public void stop() {
        channelGroup.close().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }


}
