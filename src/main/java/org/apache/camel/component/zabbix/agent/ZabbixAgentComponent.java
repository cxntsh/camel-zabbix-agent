package org.apache.camel.component.zabbix.agent;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;
import org.apache.camel.spi.Metadata;

import java.util.Map;

/**
 * @author nantian created at 2021/8/30 0:20
 * <p>
 * zabbix agent camel component
 */
public class ZabbixAgentComponent extends UriEndpointComponent {

    @Metadata(label = "security", secret = true)
    private Integer startPollers;

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        ZabbixAgentConfiguration configuration = new ZabbixAgentConfiguration();

        if (startPollers != null) {
            configuration.setStartPollers(startPollers);
        }

        setProperties(configuration, parameters);

        ZabbixAgentEndpoint endpoint = new ZabbixAgentEndpoint(uri, this, configuration);

        endpoint.setName(remaining);
        return endpoint;
    }

    public ZabbixAgentComponent() {
        super(ZabbixAgentEndpoint.class);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    public Integer getStartPollers() {
        return startPollers;
    }

    public void setStartPollers(Integer startPollers) {
        this.startPollers = startPollers;
    }
}
