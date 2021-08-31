package org.apache.camel.component.zabbix.agent;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.voovan.network.tcp.TcpServerSocket;

/**
 * @author nantian created at 2021/8/30 19:52
 */
public class ZabbixAgentConsumer extends DefaultConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ZabbixAgentEndpoint.class);

    private final ZabbixAgentConfiguration configuration;
    private       TcpServerSocket          socket;
    private       ZabbixAgentEndpoint      endpoint;

    public ZabbixAgentConsumer(ZabbixAgentEndpoint endpoint, Processor processor, ZabbixAgentConfiguration configuration) {
        super(endpoint, processor);
        this.configuration = configuration;
    }


    @Override
    public ZabbixAgentEndpoint getEndpoint() {
        return (ZabbixAgentEndpoint) super.getEndpoint();
    }


    protected void doStart() throws Exception {
        socket = new TcpServerSocket(configuration.getListenIp(), configuration.getListenPort(), configuration.getReadTimeout());
        socket.handler(new ZabbixAgentRequestHandler());
        socket.filterChain().add(new ZabbixAgentRequestFilter());
        socket.syncStart();

        LOG.info("Zaabbix Agent Started at {}:{} ", configuration.getListenIp(), configuration.getListenPort());
        super.doStart();
    }

    protected void doStop() throws Exception {
        socket.close();
        super.doStop();
    }

    /**
     * 产生 Exchange 并且处理
     *
     * @param exchange Exchange
     */
    void processExchange(final Exchange exchange) {
        boolean sync = true;
        try {
            sync = getAsyncProcessor().process(exchange, new AsyncCallback() {
                @Override
                public void done(boolean doneSync) {
                    if (exchange.getException() != null) {
                        getExceptionHandler().handleException("Error processing exchange.", exchange, exchange.getException());
                    }
                }
            });
        } catch (Throwable e) {
            exchange.setException(e);
        }

        if (sync) {
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange.", exchange, exchange.getException());
            }
        }
    }
}
