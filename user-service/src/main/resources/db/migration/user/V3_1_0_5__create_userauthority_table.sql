CREATE TABLE IF NOT EXISTS "user"."user_authority" (
  id               BIGSERIAL,
  user_id          BIGINT        NOT NULL,
  authority_id     BIGINT        NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id),
  CONSTRAINT fk_userauthority_userid FOREIGN KEY(user_id) REFERENCES "user"."user"(id),
  CONSTRAINT fk_userauthority_authorityid FOREIGN KEY(authority_id) REFERENCES "user"."authority"(id)
);

CREATE UNIQUE INDEX uniq_userauthority_userid_authorityid on "user"."user_authority"(user_id, authority_id);

CREATE TABLE IF NOT EXISTS "user"."user_authority_aud" (
  id               BIGINT,
  user_id          BIGINT        NOT NULL,
  authority_id     BIGINT        NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  rev              BIGINT        NOT NULL,
  revtype          BIGINT        NOT NULL,
  PRIMARY KEY (id, rev)
);

INSERT INTO "user"."user_authority"(user_id, authority_id, created_at, created_by, updated_at, updated_by, deleted) VALUES 
(11, 1, now(), 1, now(), 1, false),
(11, 2, now(), 1, now(), 1, false),
(11, 3, now(), 1, now(), 1, false),
(11, 4, now(), 1, now(), 1, false),
(11, 5, now(), 1, now(), 1, false),
(12, 2, now(), 1, now(), 1, false),
(12, 3, now(), 1, now(), 1, false),
(12, 4, now(), 1, now(), 1, false),
(12, 5, now(), 1, now(), 1, false),
(13, 6, now(), 1, now(), 1, false),
(13, 7, now(), 1, now(), 1, false),
(13, 8, now(), 1, now(), 1, false),
(13, 9, now(), 1, now(), 1, false),
(13, 10, now(), 1, now(), 1, false),
(13, 11, now(), 1, now(), 1, false),
(13, 12, now(), 1, now(), 1, false),
(14, 13, now(), 1, now(), 1, false),
(14, 14, now(), 1, now(), 1, false),
(14, 15, now(), 1, now(), 1, false);
