package com.ning.web.mvc.controller;

public interface WebComponentContext {

    String CONTEXT_NAME = "WebComponentContext";

    <C> C getComponent(String name);

}
