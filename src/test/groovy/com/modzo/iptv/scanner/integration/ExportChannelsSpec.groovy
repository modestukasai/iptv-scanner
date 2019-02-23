package com.modzo.iptv.scanner.integration

import com.modzo.iptv.scanner.database.Channel
import com.modzo.iptv.scanner.database.commands.ChangeChannelStatusHandler
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static com.modzo.iptv.scanner.database.Channel.Status.NOT_WORKING

class ExportChannelsSpec extends IntegrationSpec {

    void 'should export all channels as m3u playlist'() {
        given:
            Channel channel = testChannel.create()
        when:
            ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity('/channels/export', ByteArrayResource)
        then:
            response.statusCode == HttpStatus.OK
            response.headers.get('Content-Disposition')[0] == 'attachment;filename=all_channels.m3u'
        and:
            String[] lines = (new String(response.body.byteArray)).split('\\n')
            lines.size() == 3
            lines[0] == '#EXTM3U'
            lines[1] == "#EXTINF:${channel.soundTrack},${channel.name}"
            lines[2] == channel.url
    }

    void 'should export channels with status not working as m3u playlist'() {
        given:
            Channel notWorkingChannel = testChannel.create()
            changeChannelStatusHandler.handle(new ChangeChannelStatusHandler.Request(notWorkingChannel.id, NOT_WORKING))
        when:
            ResponseEntity<ByteArrayResource> response = restTemplate.getForEntity(
                    "/channels/export?status=${NOT_WORKING}",
                    ByteArrayResource
            )
        then:
            response.statusCode == HttpStatus.OK
            response.headers.get('Content-Disposition')[0] == "attachment;filename=${NOT_WORKING}_channels.m3u"
        and:
            String[] lines = (new String(response.body.byteArray)).split('\\n')
            lines.size() == 3
            lines[0] == '#EXTM3U'
            lines[1] == "#EXTINF:${notWorkingChannel.soundTrack},${notWorkingChannel.name}"
            lines[2] == notWorkingChannel.url
    }
}