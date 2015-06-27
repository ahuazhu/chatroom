package io.isharon.chat.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class CmdProcessorManager {

    private static Map<String, Processor> handlerMap = new HashMap<String, Processor>();

    static {
        init();
    }

    public static void init() {
        CmdProcessorManager.register("/天气", new WeatherHandler());
        CmdProcessorManager.register("/quit", new ByeProcessor());
    }


    public static void register(String cmd, Processor processor) {
        handlerMap.put(cmd, processor);
    }

    public static Processor getProcessor(String cmd) {
        return handlerMap.get(cmd);
    }
}
