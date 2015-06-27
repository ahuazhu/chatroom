package io.isharon.chat.codec;

import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public interface Codec {

    public static abstract class Encoder extends OneToOneEncoder {

    }

    public static abstract class Decoder extends OneToOneDecoder {

    }

    Encoder getEncoder();

    Decoder getDecoder();
}
