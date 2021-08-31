package org.apache.camel.component.zabbix.agent;

import org.apache.camel.AsyncEndpoint;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nantian created at 2021/8/30 19:50
 */

@UriEndpoint(firstVersion = "1.0.0",
        scheme = "zabbix",
        title = "zabbix-agent",
        syntax = "zabbix:agent:host:port",
        consumerClass = ZabbixAgentConsumer.class,
        label = "zabbix,agent")
public class ZabbixAgentEndpoint extends DefaultEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(ZabbixAgentEndpoint.class);

    @UriParam
    private final ZabbixAgentConfiguration configuration;

    public ZabbixAgentEndpoint(final String uri, ZabbixAgentComponent component, ZabbixAgentConfiguration properties) {
        super(uri, component);
        this.configuration = properties;
    }

    public ZabbixAgentConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    protected String createEndpointUri() {
        ObjectHelper.notNull(configuration, "configuration");
        return "zabbix:agent://" + getConfiguration().getListenIp() + ":" + getConfiguration().getListenPort();
    }

    @Override
    public Producer createProducer() throws Exception {
        return null;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        ZabbixAgentConsumer zbxConsumer = new ZabbixAgentConsumer(this, processor, configuration);
        configureConsumer(zbxConsumer);
        return zbxConsumer;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
