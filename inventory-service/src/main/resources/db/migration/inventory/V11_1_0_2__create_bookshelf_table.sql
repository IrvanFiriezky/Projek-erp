CREATE TABLE IF NOT EXISTS "inventory"."bookshelf" (
  id               BIGSERIAL, 
  bookshelf_name   VARCHAR(100)  NOT NULL,
  status           INT           NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  version          BIGINT        NOT NULL DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "inventory"."bookshelf_aud" (
  id               BIGINT,
  bookshelf_name   VARCHAR(100)  NOT NULL,
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

INSERT INTO "inventory"."bookshelf"(bookshelf_name, status, created_at, created_by, updated_at, updated_by, deleted) VALUES 
('Bookshelf 01', 1, now(), 1, now(), 1, false),
('Bookshelf 02', 1, now(), 1, now(), 1, false),
('Bookshelf 03', 1, now(), 1, now(), 1, false);
