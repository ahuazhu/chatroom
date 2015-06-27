package io.isharon.chat.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import java.util.Objects;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class JsonCodec implements Codec {

    public Encoder getEncoder() {
        return null;
    }

    public Decoder getDecoder() {
        return null;
    }

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    }

    public static class JsonEncoder extends Encoder {

        @Override
        protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object msg) throws Exception {

            return null;
        }
    }

    public static class JsonDecoder extends Decoder {

        @Override
        protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, Object msg) throws Exception {

            return null;
        }
    }
}
