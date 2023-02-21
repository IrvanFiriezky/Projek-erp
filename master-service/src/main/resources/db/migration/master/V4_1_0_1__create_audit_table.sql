CREATE SEQUENCE "master"."revinfo_seq" INCREMENT BY 50;

CREATE TABLE "master"."revinfo" (
    rev           BIGINT NOT NULL DEFAULT nextval('"master"."revinfo_seq"'),
    revtstmp      BIGINT,
    PRIMARY KEY (rev)
);
