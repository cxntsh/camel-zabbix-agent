package com.zmops.component.zabbix.agent;

import org.apache.camel.*;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nantian created at 2021/8/30 19:50
 */

@UriEndpoint(firstVersion = "1.0.0",
             scheme = "zabbix-agent",
             title = "Zabbix Agent",
             syntax = "zabbix-agent:tcp:host:port",
             consumerClass = ZabbixAgentConsumer.class,
             label = "zabbix-agent")
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
        return "zabbix-agent:tcp://" + getConfiguration().getListenIp() + ":" + getConfiguration().getListenPort();
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

    /**
     * Zabbix Server Request Item Key
     *
     * @param  itemKey keyStr
     * @return         Exchange
     */
    public Exchange createExchange(Object itemKey) {
        Exchange exchange = createExchange();

        ZabbixItem item = new ZabbixItem(itemKey.toString());

        for (int i = 0; i < item.getArgumentCount(); i++) {
            exchange.getMessage().setHeader("args-" + (i + 1), item.getArgument(i + 1));
        }
        exchange.getMessage().setHeader("key", item.getKeyId());

        exchange.getIn().setBody(itemKey);

        return exchange;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
