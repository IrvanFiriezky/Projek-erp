CREATE TABLE IF NOT EXISTS "inventory"."supply" (
  id               BIGSERIAL, 
  supply_name     VARCHAR(100)  NOT NULL,
  status           INT           NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  version          BIGINT        NOT NULL DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "inventory"."supply_aud" (
  id               BIGINT,
  supply_name     VARCHAR(100)  NOT NULL,
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

INSERT INTO "inventory"."supply"(supply_name, status, created_at, created_by, updated_at, updated_by, deleted) VALUES
('Supply 01', 1, now(), 1, now(), 1, false),
('Supply 02', 1, now(), 1, now(), 1, false),
('Supply 03', 1, now(), 1, now(), 1, false);
