package org.apache.camel.component.zabbix.agent;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import java.net.URI;
import java.util.Map;

/**
 * @author nantian created at 2021/8/30 0:20
 * <p>
 * zabbix agent camel component
 */
public class ZabbixAgentComponent extends UriEndpointComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        ZabbixAgentConfiguration configuration = new ZabbixAgentConfiguration();
        configuration.parseURI(new URI(remaining), parameters, this);

        ZabbixAgentEndpoint endpoint = new ZabbixAgentEndpoint(uri, this, configuration);
        setProperties(endpoint.getConfiguration(), parameters);
        return endpoint;
    }

    public ZabbixAgentComponent() {
        super(ZabbixAgentEndpoint.class);
    }
}
