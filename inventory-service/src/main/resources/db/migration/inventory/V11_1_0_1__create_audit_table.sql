CREATE SEQUENCE "inventory"."revinfo_seq" INCREMENT BY 50;

CREATE TABLE "inventory"."revinfo" (
    rev           BIGINT NOT NULL DEFAULT nextval('"inventory"."revinfo_seq"'),
    revtstmp      BIGINT,
    PRIMARY KEY (rev)
);
