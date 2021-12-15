package com.zmops.component.zabbix.agent;

import java.net.URI;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;

/**
 * @author nantian created at 2021/8/30 0:20
 *         <p>
 *         zabbix agent camel component
 */
@Component("zabbix-agent")
public class ZabbixAgentComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        ZabbixAgentConfiguration configuration = new ZabbixAgentConfiguration();
        configuration.parseURI(new URI(remaining), parameters, this);

        ZabbixAgentEndpoint endpoint = new ZabbixAgentEndpoint(uri, this, configuration);
        setProperties(endpoint.getConfiguration(), parameters);
        return endpoint;
    }

    public ZabbixAgentComponent() {
    }

}
