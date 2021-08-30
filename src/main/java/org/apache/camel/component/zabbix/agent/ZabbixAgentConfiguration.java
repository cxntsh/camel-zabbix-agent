package org.apache.camel.component.zabbix.agent;

import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

/**
 * @author nantian created at 2021/8/30 20:50
 */

@UriParams
public class ZabbixAgentConfiguration {

    @UriParam(defaultValue = "" + 10, description = "zabbix agent start poller thread number")
    int startPollers;

    public Integer getStartPollers() {
        return startPollers;
    }

    public void setStartPollers(Integer startPollers) {
        this.startPollers = startPollers;
    }

}
