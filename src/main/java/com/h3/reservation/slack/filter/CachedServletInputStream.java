package com.h3.reservation.slack.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedServletInputStream extends ServletInputStream {
    private final InputStream src;
    private boolean isFinished = false;

    public CachedServletInputStream(InputStream src) {
        this.src = src;
    }

    @Override
    public int read() throws IOException {
        int data = src.read();
        isFinished = (data == -1);
        return data;
    }

    @Override
    public int available() throws IOException {
        return src.available();
    }

    @Override
    public void close() throws IOException {
        super.close();
        src.close();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }
}