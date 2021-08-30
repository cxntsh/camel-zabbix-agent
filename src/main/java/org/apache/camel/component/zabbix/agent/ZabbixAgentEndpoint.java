package org.apache.camel.component.zabbix.agent;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nantian created at 2021/8/30 19:50
 */

@UriEndpoint(firstVersion = "1.0.0",
        scheme = "zabbix",
        title = "zabbix-agent",
        syntax = "zabbix:agent",
        consumerClass = ZabbixAgentConsumer.class,
        label = "zabbix")
public class ZabbixAgentEndpoint extends DefaultEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(ZabbixAgentEndpoint.class);

    @UriPath(description = "zabbix agent name")
    @Metadata(required = "true")
    private String name;

    @UriParam
    private final ZabbixAgentConfiguration configuration;

    public ZabbixAgentEndpoint(final String uri, ZabbixAgentComponent component, ZabbixAgentConfiguration properties) {
        super(uri, component);
        this.configuration = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void doStart() throws Exception {

        LOG.info("MQTT Connection connected to {}", configuration.getStartPollers());
        LOG.info("MQTT Connection connected to {}", getName());

        super.doStart();
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        ZabbixAgentConsumer zbxConsumer = new ZabbixAgentConsumer(this, processor);
        configureConsumer(zbxConsumer);
        return zbxConsumer;
    }

    public ZabbixAgentConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Producer createProducer() throws Exception {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
