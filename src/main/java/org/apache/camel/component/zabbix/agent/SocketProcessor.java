package org.apache.camel.component.zabbix.agent;

import java.net.Socket;

/**
 * @author nantian created at 2021/8/31 15:25
 */
public class SocketProcessor implements Runnable {

    private final Socket socket;

    SocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
