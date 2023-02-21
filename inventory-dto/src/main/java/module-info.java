module id.cranium.erp.inventory.dto {
    requires spring.boot; // spring-boot-3.0.1.jar
    requires spring.boot.autoconfigure; // spring-boot-autoconfigure-3.0.1.jar
    requires spring.web; // spring-web-6.0.3.jar
    requires transitive spring.webmvc; // spring-webmvc-6.0.3.jar
    requires transitive spring.context; // spring-context-6.0.3.jar
    requires spring.core; // spring-core-6.0.3.jar
    requires spring.beans; // spring-beans-6.0.3.jar
    requires com.fasterxml.jackson.annotation; // jackson-annotations-2.14.1.jar
    requires com.fasterxml.jackson.databind; // jackson-databind-2.14.1.jar
    requires transitive jakarta.validation; // jakarta.validation-api-3.0.2.jar
    requires org.slf4j; // slf4j-api-2.0.6.jar
    requires org.hibernate.validator; // hibernate-validator-8.0.0.Final.jar
    requires static lombok; // lombok-1.18.24.jar
    requires id.cranium.erp.starter.dto;

    opens id.cranium.erp.inventory.configurationdto to spring.core, spring.beans, spring.context;
    opens id.cranium.erp.inventory.inventorydto to spring.core, spring.beans, spring.context;
    opens id.cranium.erp.inventory.dto to com.fasterxml.jackson.databind, org.hibernate.validator;
    opens id.cranium.erp.inventory.validator.constraint to org.hibernate.validator;

    exports id.cranium.erp.inventory.dto;
    exports id.cranium.erp.inventory.event;
    exports id.cranium.erp.inventory.enums;
    exports id.cranium.erp.inventory.validator.constraint;
}
