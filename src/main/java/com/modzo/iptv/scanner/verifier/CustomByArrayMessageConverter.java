package com.modzo.iptv.scanner.verifier;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

import java.io.IOException;

public class CustomByArrayMessageConverter extends ByteArrayHttpMessageConverter {

    @Override
    public byte[] readInternal(Class<? extends byte[]> clazz, HttpInputMessage inputMessage) throws IOException {
        return new byte[]{1, 10, 1};
    }
}
