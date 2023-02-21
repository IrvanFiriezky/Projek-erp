CREATE SEQUENCE "user"."revinfo_seq" INCREMENT BY 50;

CREATE TABLE "user"."revinfo" (
    rev           BIGINT NOT NULL DEFAULT nextval('"user"."revinfo_seq"'),
    revtstmp      BIGINT,
    PRIMARY KEY (rev)
);
