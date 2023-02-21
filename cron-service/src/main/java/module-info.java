module id.cranium.erp.cron {
    requires spring.boot; //spring-boot-3.0.1.jar
	requires spring.boot.autoconfigure; //spring-boot-autoconfigure-3.0.1.jar
    requires spring.web; //spring-web-6.0.3.jar
    requires spring.webmvc; //spring-webmvc-6.0.3.jar
    requires spring.context; //spring-context-6.0.3.jar
    requires spring.tx; //spring-tx-6.0.3.jar
    requires spring.beans; //spring-beans-6.0.3.jar
    requires spring.data.jpa; //spring-data-jpa-3.0.0.jar
    requires spring.data.commons; //spring-data-commons-3.0.0.jar
    requires spring.data.envers; //spring-data-envers-3.0.1.jar
    requires spring.core; //spring-core-6.0.3.jar
	requires spring.orm; //spring-orm-6.0.3.jar
    requires spring.aop; //spring-aop-6.0.3.jar
    requires spring.security.core; // spring-security-core-6.0.1.jar
	requires spring.security.web; //spring-security-web-6.0.1.jar
	requires spring.security.config; //spring-security-config-6.0.1.jar
	requires spring.security.crypto; //spring-security-crypto-6.0.1.jar
    requires reactor.core; //reactor-core-3.5.1.jar
    requires static lombok; //lombok-1.18.24.jar
    requires jakarta.persistence; //jakarta.persistence-api-3.1.0.jar
	requires jakarta.annotation; //jakarta.annotation-api-2.1.1.jar
	requires jakarta.validation; //jakarta.validation-api-3.0.2.jar
	requires jakarta.servlet; //jakarta.servlet-api-6.0.0.jar
	requires jjwt.api; //jjwt-api-0.11.5.jar
	requires orika.core; //orika-core-1.5.4.jar
	requires org.hibernate.orm.core; //hibernate-core-6.1.6.Final.jar
	requires org.hibernate.orm.envers; //hibernate-envers-6.1.6.Final.jar
    requires org.hibernate.validator; //hibernate-validator-8.0.0.Final.jar
	requires org.flywaydb.core;
	requires org.slf4j; //slf4j-api-2.0.6.jar
	requires org.apache.commons.lang3; //commons-lang3-3.12.0.jar
	requires com.fasterxml.jackson.databind; //jackson-databind-2.14.1.jar
    requires org.reactivestreams; //reactive-streams-1.0.4.jar
    requires id.cranium.erp.starter;
	requires id.cranium.erp.starter.dto;
    requires id.cranium.erp.user.dto;
    requires id.cranium.erp.master.dto;
    opens db.migration.cron;
    opens db.test.cron;
    opens id.cranium.erp.cron.cronservice to spring.core,spring.beans,spring.context;
    opens id.cranium.erp.cron.configuration to spring.core,spring.beans,spring.context;
    opens id.cranium.erp.cron.scheduler.user to spring.core,spring.beans;
    opens id.cranium.erp.cron.scheduler.master to spring.core,spring.beans;
    //opens id.cranium.erp.cron.security to spring.core,spring.beans;
    //opens id.cranium.erp.cron.exception to spring.core;
    //opens id.cranium.erp.cron.service to spring.core;
    //opens id.cranium.erp.cron.validator to org.hibernate.validator;
	//opens id.cranium.erp.cron.util to spring.core,spring.beans;
    opens id.cranium.erp.cron.eventlistener to spring.core,spring.beans;
    opens id.cranium.erp.cron.client.user to spring.core,spring.beans;
}
