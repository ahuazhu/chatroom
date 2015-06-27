package io.isharon.chat.handler;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.isharon.chat.assets.User;
import io.isharon.chat.model.Message;
import io.isharon.chat.util.ObjectMapperFactory;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class WeatherHandler extends DefaultProcessor {
    final String fmt = "http://v.juhe.cn/weather/index?format=2&key=f5b3ee78081795f4cfe6053dd2887f77&cityname=%s";

    static class WeatherResult {
        Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
    }

    static class Result {
        private Today today;

        public Today getToday() {
            return today;
        }

        public void setToday(Today today) {
            this.today = today;
        }
    }

    static class Today {
        String temperature;
        String weather;

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }
    }

    Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000)
            .expireAfterAccess(2, TimeUnit.HOURS).build();

    @Override
    protected void doProcess(ChannelHandlerContext ctx, ChannelEvent e, Message message) {
        final String[] args = message.getContent().split("\\s+");
        if (args.length < 2) return;

        try {

            String weather = cache.get(args[1], new Callable<String>() {
                public String call() {
                    try {
                        String key = String.format(fmt, args[1]);
                        URL url = null;
                        url = new URL(key);

                        WeatherResult weatherResult = ObjectMapperFactory.getMapper().readValue(new InputStreamReader(url.openStream()), WeatherResult.class);

                        String content = weatherResult.result.today.weather + "  " + weatherResult.result.today.getTemperature();

                        return content;
                    } catch (Exception e) {
                        return "";
                    }
                }
            });


            sendToUser(User.findUser(message.getUserName()), systemMessage(weather, Message.Type.CHAT));

            return;

        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
