CREATE TABLE IF NOT EXISTS "master"."stock" (
  id               BIGSERIAL, 
  product_id       BIGINT        NOT NULL,
  total_stock      BIGINT        NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id),
  CONSTRAINT fk_stock_productid FOREIGN KEY(product_id) REFERENCES "master"."product"(id)
);

CREATE UNIQUE INDEX uniq_stock_productid on "master"."stock"(product_id);

CREATE TABLE IF NOT EXISTS "master"."stock_aud" (
  id               BIGINT,
  product_id       BIGINT        NOT NULL,
  total_stock      BIGINT        NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  rev              BIGINT        NOT NULL,
  revtype          BIGINT        NOT NULL,
  PRIMARY KEY (id, rev)
);

INSERT INTO "master"."stock"(product_id, total_stock, created_at, created_by, updated_at, updated_by, deleted) VALUES 
(1, 10, now(), 1, now(), 1, false),
(2, 20, now(), 1, now(), 1, false),
(3, 30, now(), 1, now(), 1, false);
