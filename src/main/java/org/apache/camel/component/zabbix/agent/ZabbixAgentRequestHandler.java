package org.apache.camel.component.zabbix.agent;

import org.voovan.network.IoHandler;
import org.voovan.network.IoSession;

/**
 * @author nantian created at 2021/8/31 17:47
 */
public class ZabbixAgentRequestHandler implements IoHandler {

    @Override
    public Object onConnect(IoSession session) {
        return null;
    }

    @Override
    public void onDisconnect(IoSession session) {

    }

    @Override
    public Object onReceive(IoSession session, Object obj) {
        return null;
    }

    @Override
    public void onSent(IoSession session, Object obj) {

    }

    @Override
    public void onFlush(IoSession session) {

    }

    @Override
    public void onException(IoSession session, Exception e) {

    }

    @Override
    public void onIdle(IoSession session) {

    }
}
