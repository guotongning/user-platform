package org.geektimes.rest.demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class RestClientDemo {

    public static void main(String[] args) {
//        get();
        post();
    }

    private static void get() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .get();
        //  Response
        String result = response.readEntity(String.class);
        System.out.println(result);
    }

    private static void post() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/rest/test")      // WebTarget
                .request() // Invocation.Builder
                .post(Entity.json("{\n" +
                        "\"name\":\"Nicholas\",\n" +
                        "\"password\":\"123456\",\n" +
                        "\"email\":\"guotongning@126.com\",\n" +
                        "\"phoneNumber\":\"17601010101\"\n" +
                        "}"));
        //  Response
        Object result = response.getEntity();

        System.out.println(result);
    }
}
