module albumfinder.initial {
    requires javax.annotation.api;
    requires java.sql;
    requires java.naming;
    requires java.validation;

    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.jdbc;
    requires spring.web;
    requires spring.tx;
    requires spring.boot;
    requires spring.security.web;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.boot.autoconfigure;
    requires spring.orm;

    requires hibernate.core;
    requires hibernate.jpa;
    requires jackson.annotations;
    requires commons.lang3;
    requires slf4j.api;
    requires tomcat.embed.core;


    exports com.ferzerkerx.albumfinder;
}