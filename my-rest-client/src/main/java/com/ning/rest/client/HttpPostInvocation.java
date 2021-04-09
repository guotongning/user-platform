package com.ning.rest.client;

import com.ning.rest.core.DefaultResponse;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @Author: nicholas
 * @Date: 2021/3/30 21:05
 * @Descreption:
 */
public class HttpPostInvocation implements Invocation {

    private final URI uri;

    private URL url;

    private final MultivaluedMap<String, Object> headers;

    private final Entity<?> body;

    public HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> body) {
        this.uri = uri;
        this.headers = headers;
        this.body = body;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Invocation property(String name, Object value) {
        return null;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST);
            setRequestHeaders(connection);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(1000);
            connection.getOutputStream().write(body.getEntity().toString().getBytes(StandardCharsets.UTF_8));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();

            byte[] buffer = new byte[1024];
            StringBuffer sb = new StringBuffer();
            InputStream in = connection.getInputStream();
            while (in.read(buffer, 0, 1024) != -1) {
                sb.append(new String(buffer));
            }

            int statusCode = connection.getResponseCode();

            DefaultResponse response = new DefaultResponse();
            response.setConnection(connection);
            response.setStatus(statusCode);

            response.setEntity(sb.toString());
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setRequestHeaders(HttpURLConnection connection) {
        setRequestHeaders(connection, headers);
    }

    static void setRequestHeaders(HttpURLConnection connection, MultivaluedMap<String, Object> headers) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return null;
    }
}
