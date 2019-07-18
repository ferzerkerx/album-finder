module ferzerkerx.albumfinder {
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.jdbc;
    requires spring.orm;
    requires java.sql;
    requires java.validation;
    requires spring.boot.autoconfigure;
    requires spring.tx;
    requires spring.boot;
    requires spring.security.web;
    requires tomcat.embed.core;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.web;
    requires slf4j.api;
    requires java.naming;
    requires java.persistence;
    requires jackson.annotations;
    requires org.hibernate.orm.core;
    requires org.apache.commons.lang3;

    exports com.ferzerkerx.albumfinder;
}