package com.zmops.component.zabbix.agent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.voovan.network.IoFilter;
import org.voovan.network.IoSession;
import org.voovan.network.exception.IoFilterException;

/**
 * @author nantian created at 2021/8/31 18:18
 *         <p>
 *         zabbix agent message protocol
 */
public class ZabbixAgentRequestFilter implements IoFilter {

    private static final byte[] PROTOCOL_HEADER = { 'Z', 'B', 'X', 'D', '\1' };
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    @Override
    public Object decode(IoSession session, Object object) throws IoFilterException {
        if (object instanceof ByteBuffer) {
            ByteBuffer reqBuf = (ByteBuffer) object;

            byte[] bytes = new byte[reqBuf.limit()];
            reqBuf.get(bytes, 0, bytes.length);
            reqBuf.clear();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(inputStream);

            byte[] data = new byte[5];
            try {
                dis.readFully(data);

                data = new byte[8];
                dis.readFully(data);

                ByteBuffer buffer = ByteBuffer.wrap(data);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                long length = buffer.getLong();

                data = new byte[(int) length];
                dis.readFully(data);

                return new String(data, UTF8_CHARSET);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object encode(IoSession session, Object object) throws IoFilterException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] data, responseBytes;

        data = PROTOCOL_HEADER;
        baos.write(data, 0, data.length);

        responseBytes = object.toString().getBytes(UTF8_CHARSET);
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(responseBytes.length);
        data = buffer.array();
        baos.write(data, 0, data.length);

        data = responseBytes;
        baos.write(data, 0, data.length);

        return ByteBuffer.wrap(baos.toByteArray());
    }

}
