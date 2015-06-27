package io.isharon.chat.handler;

import io.isharon.chat.model.Message;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class ChartProcessor extends DefaultProcessor {

    public void doProcess(ChannelHandlerContext ctx, ChannelEvent e, Message message) {

        if (message.getContent().startsWith("/")) {
            String[] parts = message.getContent().split("\\s+");

            Processor processor = CmdProcessorManager.getProcessor(parts[0]);

            if (processor != null) {
                processor.process(ctx, e, message);
                return;
            }


        }

        broadCast(message);

    }
}
