module id.cranium.erp.web {
	requires spring.boot; //spring-boot-3.0.1.jar
	requires spring.boot.autoconfigure; //spring-boot-autoconfigure-3.0.1.jar
	requires spring.context; //spring-context-6.0.3.jar
	requires spring.beans; //spring-beans-6.0.3.jar
	requires spring.core; //spring-core-6.0.3.jar
	requires id.cranium.erp.starter.dto;
	requires id.cranium.erp.starter;
	requires id.cranium.erp.user.dto;
    requires id.cranium.erp.user;
	requires id.cranium.erp.master.dto;
	requires id.cranium.erp.master;
	opens id.cranium.erp.web.webservice to spring.core,spring.beans,spring.context;
	opens id.cranium.erp.web.configuration to spring.core,spring.beans,spring.context;
}