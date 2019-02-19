package com.modzo.iptv.scanner.database.commands;

import com.modzo.iptv.scanner.database.Channel;
import com.modzo.iptv.scanner.database.Channels;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateChannelHandler {

    private final Channels channels;

    public CreateChannelHandler(Channels channels) {
        this.channels = channels;
    }

    @Transactional
    public Response handle(Request request) {
        Channel channel = new Channel();
        channel.setName(request.name);
        channel.setSoundTrack(request.soundTrack);
        channel.setUrl(request.url);

        Channel savedChannel = channels.save(channel);
        return new Response(savedChannel.getId());
    }

    public static class Response {
        private final Long id;

        Response(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    public static class Request {
        private final String name;

        private final int soundTrack;

        private final String url;

        public Request(String name, int soundTrack, String url) {
            this.name = name;
            this.soundTrack = soundTrack;
            this.url = url;
        }
    }
}
