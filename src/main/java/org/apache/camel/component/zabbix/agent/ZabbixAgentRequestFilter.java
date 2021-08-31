package org.apache.camel.component.zabbix.agent;

import org.voovan.network.IoFilter;
import org.voovan.network.IoSession;
import org.voovan.network.exception.IoFilterException;

/**
 * @author nantian created at 2021/8/31 18:18
 */
public class ZabbixAgentRequestFilter implements IoFilter {

    @Override
    public Object decode(IoSession session, Object object) throws IoFilterException {
        return null;
    }

    @Override
    public Object encode(IoSession session, Object object) throws IoFilterException {
        return null;
    }

}
