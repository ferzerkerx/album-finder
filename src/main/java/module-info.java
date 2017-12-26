module albumfinder.initial {
    requires java.sql;

    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires spring.tx;
    requires spring.webmvc;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires java.validation;
    requires spring.web;
    requires tomcat.embed.core;
    requires spring.security.web;
    requires spring.security.config;
    requires jackson.annotations;
    requires commons.lang3;
    requires slf4j.api;
    requires spring.jdbc;
    requires spring.security.core;

    requires java.naming;
    requires spring.orm;
    requires hibernate.core;
    requires hibernate.jpa;


    exports com.ferzerkerx.albumfinder;
}