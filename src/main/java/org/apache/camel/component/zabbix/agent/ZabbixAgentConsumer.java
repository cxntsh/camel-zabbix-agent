package org.apache.camel.component.zabbix.agent;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

/**
 * @author nantian created at 2021/8/30 19:52
 */
public class ZabbixAgentConsumer extends DefaultConsumer {

    public ZabbixAgentConsumer(ZabbixAgentEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }


    @Override
    public ZabbixAgentEndpoint getEndpoint() {
        return (ZabbixAgentEndpoint) super.getEndpoint();
    }


    protected void doStart() throws Exception {
        super.doStart();
    }

    protected void doStop() throws Exception {
        super.doStop();
    }

    void processExchange(final Exchange exchange) {
        boolean sync = true;
        try {
            sync = getAsyncProcessor().process(exchange, new AsyncCallback() {
                @Override
                public void done(boolean doneSync) {
                    if (exchange.getException() != null) {
                        getExceptionHandler().handleException("Error processing exchange.", exchange, exchange.getException());
                    }
                }
            });
        } catch (Throwable e) {
            exchange.setException(e);
        }

        if (sync) {
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange.", exchange, exchange.getException());
            }
        }
    }
}
