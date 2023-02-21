CREATE TABLE IF NOT EXISTS "user"."user_scope" (
  id               BIGSERIAL,
  user_id          BIGINT        NOT NULL,
  scope_id         BIGINT        NOT NULL,
  scope_value      VARCHAR(255),
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id),
  CONSTRAINT fk_userscope_userid FOREIGN KEY(user_id) REFERENCES "user"."user"(id),
  CONSTRAINT fk_userscope_scopeid FOREIGN KEY(scope_id) REFERENCES "user"."scope"(id)
);

CREATE UNIQUE INDEX uniq_userscope_userid_scopeid on "user"."user_scope"(user_id, scope_id);

CREATE TABLE IF NOT EXISTS "user"."user_scope_aud" (
  id               BIGINT,
  user_id          BIGINT        NOT NULL,
  scope_id         BIGINT        NOT NULL,
  scope_value      VARCHAR(255),
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  rev              BIGINT        NOT NULL,
  revtype          BIGINT        NOT NULL,
  PRIMARY KEY (id, rev)
);

INSERT INTO "user"."user_scope"(user_id, scope_id, scope_value, created_at, created_by, updated_at, updated_by, deleted) VALUES 
(14, 4, '14', now(), 1, now(), 1, false),
(14, 7, '[2,6,8]', now(), 1, now(), 1, false);
