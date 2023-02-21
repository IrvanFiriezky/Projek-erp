module id.cranium.erp.starter {
    requires transitive spring.boot; //spring-boot-3.0.1.jar
	requires transitive spring.boot.autoconfigure; //spring-boot-autoconfigure-3.0.1.jar
    requires transitive spring.web; //spring-web-6.0.3.jar
    requires transitive spring.webmvc; //spring-webmvc-6.0.3.jar
    requires transitive spring.context; //spring-context-6.0.3.jar
    requires transitive spring.tx; //spring-tx-6.0.3.jar
    requires spring.core; //spring-core-6.0.3.jar
    requires spring.beans; //spring-beans-6.0.3.jar
	requires transitive spring.orm; //spring-orm-6.0.3.jar
    requires spring.data.jpa; //spring-data-jpa-3.0.0.jar
	requires spring.data.commons; //spring-data-commons-3.0.0.jar
    requires spring.data.envers; //spring-data-envers-3.0.1.jar
    requires transitive spring.aop; //spring-aop-6.0.3.jar
    requires transitive spring.webflux; //spring-boot-starter-webflux-3.0.1.jar
    requires transitive spring.security.core; // spring-security-core-6.0.1.jar
    requires spring.security.web; //spring-security-web-6.0.1.jar
    requires transitive jjwt.api; //jjwt-api-0.11.5.jar
    requires transitive org.aspectj.weaver; // aspectjweaver-1.9.19.jar
    requires transitive jakarta.persistence; //jakarta.persistence-api-3.1.0.jar
    requires transitive jakarta.servlet; //jakarta.servlet-api-6.0.0.jar
    requires jakarta.validation; //jakarta.validation-api-3.0.2.jar
    requires org.hibernate.orm.envers; //hibernate-envers-6.1.6.Final.jar
    requires org.hibernate.validator; //hibernate-validator-8.0.0.Final.jar
    requires io.netty.handler; //netty-handler-4.1.86.Final.jar
    requires io.netty.transport; //netty-transport-4.1.86.Final.jar
    requires io.netty.buffer; //netty-buffer-4.1.87.Final.jar
    requires reactor.netty.core; //reactor-netty-core-1.1.1.jar
    requires reactor.netty.http; //reactor-netty-http-1.1.1.jar
    requires reactor.core; //reactor-core-3.5.1.jar
    requires static lombok; //lombok-1.18.24.jar
    requires org.slf4j; //slf4j-api-2.0.6.jar
    requires com.fasterxml.jackson.annotation; //jackson-annotations-2.14.1.jar
    requires org.apache.commons.lang3; //commons-lang3-3.12.0.jar
    requires com.fasterxml.jackson.databind; //jackson-databind-2.14.1.jar
    requires org.reactivestreams; //reactive-streams-1.0.4.jar
    requires org.apache.httpcomponents.client5.httpclient5; //httpclient5-5.1.4.jar
	requires org.apache.httpcomponents.core5.httpcore5; //httpcore5-5.1.5.jar
    requires transitive id.cranium.erp.starter.dto;
    opens messages.starter;
    opens db.test.starter;
    opens id.cranium.erp.starter.starterservice to spring.core,spring.beans,spring.context;
    opens id.cranium.erp.starter.configuration to spring.core,spring.beans,spring.context;
    opens id.cranium.erp.starter.configuration.resttemplate to spring.core,spring.beans,spring.context;
    opens id.cranium.erp.starter.configuration.webflux to spring.core,spring.beans,spring.context;
    opens id.cranium.erp.starter.security to spring.core,spring.beans;
    opens id.cranium.erp.starter.exception to spring.core;
    opens id.cranium.erp.starter.service to spring.core;
    //opens id.cranium.erp.starter.validator to org.hibernate.validator;
	opens id.cranium.erp.starter.util to spring.core,spring.beans;
    opens id.cranium.erp.starter.client.auth to spring.core,spring.beans;
    opens id.cranium.erp.starter.client.user to spring.core,spring.beans;
    exports id.cranium.erp.starter.configuration;
    exports id.cranium.erp.starter.configuration.aspect;
    exports id.cranium.erp.starter.configuration.resttemplate;
    exports id.cranium.erp.starter.configuration.webflux;
    exports id.cranium.erp.starter.security;
    exports id.cranium.erp.starter.security.annotation;
    exports id.cranium.erp.starter.entity;
    exports id.cranium.erp.starter.eventlistener;
    exports id.cranium.erp.starter.exception;
    exports id.cranium.erp.starter.service;
    exports id.cranium.erp.starter.util;
}
