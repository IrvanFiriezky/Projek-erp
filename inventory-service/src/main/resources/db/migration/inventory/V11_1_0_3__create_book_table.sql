CREATE TABLE IF NOT EXISTS "inventory"."book" (
  id               BIGSERIAL, 
  bookshelf_id     BIGINT        NOT NULL,
  book_name        VARCHAR(100)  NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id),
  CONSTRAINT fk_book_bookshelfid FOREIGN KEY(bookshelf_id) REFERENCES "inventory"."bookshelf"(id)
);

CREATE UNIQUE INDEX uniq_book_bookshelfid on "inventory"."book"(bookshelf_id);

CREATE TABLE IF NOT EXISTS "inventory"."book_aud" (
  id               BIGINT,
  bookshelf_id     BIGINT        NOT NULL,
  book_name        VARCHAR(100)  NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  rev              BIGINT        NOT NULL,
  revtype          BIGINT        NOT NULL,
  PRIMARY KEY (id, rev)
);

INSERT INTO "inventory"."book"(bookshelf_id, book_name, created_at, created_by, updated_at, updated_by, deleted) VALUES 
(1, 'book 01', now(), 1, now(), 1, false),
(2, 'book 02', now(), 1, now(), 1, false),
(3, 'book 03', now(), 1, now(), 1, false);
