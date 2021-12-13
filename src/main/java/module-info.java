open module ferzerkerx.albumfinder {
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.jdbc;
    requires spring.orm;
    requires java.sql;
    requires spring.boot.autoconfigure;
    requires spring.tx;
    requires spring.boot;
    requires spring.security.web;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.web;
    requires java.naming;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.annotation;
    requires javax.servlet.api;
    requires java.validation;
    requires org.slf4j;
    requires spring.security.crypto;

    exports com.ferzerkerx.albumfinder;
}