CREATE TABLE IF NOT EXISTS "user"."scope" (
  id               BIGSERIAL,
  scope_name       VARCHAR(255)  NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uniq_scope_scopeName on "user"."scope"(scope_name);

CREATE TABLE IF NOT EXISTS "user"."scope_aud" (
  id               BIGINT,
  scope_name       VARCHAR(255)  NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  rev              BIGINT        NOT NULL,
  revtype          BIGINT        NOT NULL,
  PRIMARY KEY (id, rev)
);

INSERT INTO "user"."scope"(scope_name, created_at, created_by, updated_at, updated_by, deleted) VALUES 
('USER_PROFILE_OWN', now(), 1, now(), 1, false),
('USER_AUTHORITY_OWN', now(), 1, now(), 1, false),
('USER_SCOPE_OWN', now(), 1, now(), 1, false),
('MASTER_PRODUCT_OWN', now(), 1, now(), 1, false),
('MASTER_STOCK_OWN', now(), 1, now(), 1, false),
('PURCHASING_ORDER_OWN', now(), 1, now(), 1, false),
('MASTER_PRODUCT_CATEGORY', now(), 1, now(), 1, false);
