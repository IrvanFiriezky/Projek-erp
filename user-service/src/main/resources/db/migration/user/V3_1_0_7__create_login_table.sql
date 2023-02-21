CREATE TABLE IF NOT EXISTS "user"."login" (
  user_id          BIGINT        NOT NULL,
  username         VARCHAR(50)   NOT NULL,
  access_token     VARCHAR(255)  NOT NULL,
  status           BOOLEAN       NOT NULL,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false
);

CREATE UNIQUE INDEX uniq_login_accesstoken on "user"."login"(access_token);
CREATE INDEX idx_login_username_createdat on "user"."login"(username, created_at);
