CREATE TABLE IF NOT EXISTS "master"."product" (
  id               BIGSERIAL, 
  product_name     VARCHAR(100)  NOT NULL,
  status           INT           NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  version          BIGINT        NOT NULL DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "master"."product_aud" (
  id               BIGINT,
  product_name     VARCHAR(100)  NOT NULL,
  status           INT           NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  rev              BIGINT        NOT NULL,
  revtype          BIGINT        NOT NULL,
  PRIMARY KEY (id, rev)
);

INSERT INTO "master"."product"(product_name, status, created_at, created_by, updated_at, updated_by, deleted) VALUES 
('Product 01', 1, now(), 1, now(), 1, false),
('Product 02', 1, now(), 1, now(), 1, false),
('Product 03', 1, now(), 1, now(), 1, false);
