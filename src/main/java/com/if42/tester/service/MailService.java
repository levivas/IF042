package com.if42.tester.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class MailService{

    protected String username;
    protected String pass;
    protected boolean auth;
    protected boolean starttlsEnable;
    protected String host;
    protected String port;
    protected String subject;


    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public boolean isAuth() {
        return auth;
    }

    public boolean isStarttlsEnable() {
        return starttlsEnable;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getSubject() {
        return subject;
    }

    @Value("${mail.username}")
    public void setUsername(String username) {
        this.username = username;
    }

    @Value("${mail.pass}")
    public void setPass(String pass) {
        this.pass = pass;
    }

    @Value("${mail.auth}")
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    @Value("${mail.starttlsEnable}")
    public void setStarttlsEnable(boolean starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    @Value("${mail.host}")
    public void setHost(String host) {
        this.host = host;
    }

    @Value("${mail.port}")
    public void setPort(String port) {
        this.port = port;
    }

    @Value("${mail.subject}")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public abstract void send(String email, String password);

}

