package org.apache.camel.component.zabbix.agent;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

/**
 * @author nantian created at 2021/8/30 0:20
 * <p>
 * zabbix agent camel component
 */
public class ZabbixAgentComponent extends DefaultConsumer {

    public ZabbixAgentComponent(Endpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }


}
