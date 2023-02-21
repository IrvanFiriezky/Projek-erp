CREATE TABLE IF NOT EXISTS "user"."authority" (
  id               BIGSERIAL,
  authority_name   VARCHAR(255)  NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uniq_authority_authorityName on "user"."authority"(authority_name);

CREATE TABLE IF NOT EXISTS "user"."authority_aud" (
  id               BIGINT,
  authority_name   VARCHAR(255)  NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  rev              BIGINT        NOT NULL,
  revtype          BIGINT        NOT NULL,
  PRIMARY KEY (id, rev)
);

INSERT INTO "user"."authority"(authority_name, created_at, created_by, updated_at, updated_by, deleted) VALUES 
('ROLE_ERP_ADMIN', now(), 1, now(), 1, false),
('ROLE_ERP_CREATE', now(), 1, now(), 1, false),
('ROLE_ERP_READ', now(), 1, now(), 1, false),
('ROLE_ERP_UPDATE', now(), 1, now(), 1, false),
('ROLE_ERP_DELETE', now(), 1, now(), 1, false),
('USER_PROFILE_UPDATE', now(), 1, now(), 1, false),
('USER_PROFILE_READ', now(), 1, now(), 1, false),
('USER_PROFILE_DELETE', now(), 1, now(), 1, false),
('USER_AUTHORITY_CREATE', now(), 1, now(), 1, false),
('USER_AUTHORITY_READ', now(), 1, now(), 1, false),
('USER_SCOPE_CREATE', now(), 1, now(), 1, false),
('USER_SCOPE_READ', now(), 1, now(), 1, false),
('MASTER_PRODUCT_READ', now(), 1, now(), 1, false),
('MASTER_STOCK_READ', now(), 1, now(), 1, false),
('PURCHASING_ORDER_READ', now(), 1, now(), 1, false);
