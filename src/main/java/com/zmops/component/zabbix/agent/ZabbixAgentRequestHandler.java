package com.zmops.component.zabbix.agent;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.voovan.network.IoHandler;
import org.voovan.network.IoSession;

/**
 * @author nantian created at 2021/8/31 17:47
 */
@Slf4j
public class ZabbixAgentRequestHandler implements IoHandler {

    private final ZabbixAgentConsumer consumer;

    public ZabbixAgentRequestHandler(ZabbixAgentConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public Object onConnect(IoSession session) {
        return null;
    }

    @Override
    public void onDisconnect(IoSession session) {

    }

    @Override
    public Object onReceive(IoSession session, Object obj) {
        final Exchange exchange = consumer.getEndpoint().createExchange(obj);

        try {
            consumer.createUoW(exchange);
            consumer.getProcessor().process(exchange);
            return exchange.getMessage().getBody();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.doneUoW(exchange);
        }

        return null;
    }

    @Override
    public void onSent(IoSession session, Object obj) {
        log.debug("message: [ {} ] on sent.", obj);
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
