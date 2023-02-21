CREATE TABLE IF NOT EXISTS "inventory"."stock" (
  id               BIGSERIAL,
  supply_id       BIGINT        NOT NULL,
  total_stock      BIGINT        NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id),
  CONSTRAINT fk_stock_supplyid FOREIGN KEY(supply_id) REFERENCES "inventory"."supply"(id)
);

CREATE UNIQUE INDEX uniq_stock_supplyid on "inventory"."stock"(supply_id);

CREATE TABLE IF NOT EXISTS "inventory"."stock_aud" (
  id               BIGINT,
  supply_id       BIGINT        NOT NULL,
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

INSERT INTO "inventory"."stock"(supply_id, total_stock, created_at, created_by, updated_at, updated_by, deleted) VALUES 
(1, 10, now(), 1, now(), 1, false),
(2, 20, now(), 1, now(), 1, false),
(3, 30, now(), 1, now(), 1, false);
