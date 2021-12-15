package com.zmops.component.zabbix.agent;

import org.apache.camel.Processor;
import org.apache.camel.support.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.voovan.network.tcp.TcpServerSocket;

/**
 * @author nantian created at 2021/8/30 19:52
 */
public class ZabbixAgentConsumer extends DefaultConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ZabbixAgentEndpoint.class);

    private final ZabbixAgentConfiguration configuration;
    private TcpServerSocket socket;
    private ZabbixAgentEndpoint endpoint;

    public ZabbixAgentConsumer(ZabbixAgentEndpoint endpoint, Processor processor, ZabbixAgentConfiguration configuration) {
        super(endpoint, processor);
        this.configuration = configuration;
    }

    @Override
    public ZabbixAgentEndpoint getEndpoint() {
        return (ZabbixAgentEndpoint) super.getEndpoint();
    }

    @Override
    protected void doStart() throws Exception {
        socket = new TcpServerSocket(
                configuration.getListenIp(), configuration.getListenPort(), configuration.getReadTimeout());
        socket.handler(new ZabbixAgentRequestHandler(this));
        socket.filterChain().add(new ZabbixAgentRequestFilter());
        socket.syncStart();

        LOG.info("Zaabbix Agent Started at {}:{} ", configuration.getListenIp(), configuration.getListenPort());
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        socket.close();
        super.doStop();
    }
}
