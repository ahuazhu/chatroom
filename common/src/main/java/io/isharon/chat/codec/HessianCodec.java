package io.isharon.chat.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class HessianCodec implements Codec{

    public Encoder getEncoder() {
        return new HessianEncoder();
    }

    public Decoder getDecoder() {
        return new HessianDecoder();
    }


    public static class HessianEncoder extends Encoder {

        @Override
        protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {
            return null;
        }
    }

    public static class HessianDecoder extends Decoder {

        @Override
        protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {
            return null;
        }
    }

}
