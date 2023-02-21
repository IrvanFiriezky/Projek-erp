CREATE TABLE IF NOT EXISTS "user"."user" (
  id               BIGSERIAL, 
  username         VARCHAR(50)   NOT NULL,
  password         VARCHAR(255)  NOT NULL,
  first_name       VARCHAR(30)   NOT NULL,
  middle_name      VARCHAR(30),
  last_name        VARCHAR(30),
  email            VARCHAR(50)   NOT NULL,
  mobile_phone     VARCHAR(20)   NOT NULL,
  domain           VARCHAR(255)  NOT NULL,
  status           INT           NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL,
  created_by       BIGINT        NOT NULL,
  updated_at       TIMESTAMP     NOT NULL,
  updated_by       BIGINT        NOT NULL,
  deleted          BOOLEAN       NOT NULL DEFAULT false,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uniq_user_username on "user"."user"(username);

CREATE TABLE IF NOT EXISTS "user"."user_aud" (
  id               BIGINT, 
  username         VARCHAR(50)   NOT NULL,
  password         VARCHAR(255)  NOT NULL,
  first_name       VARCHAR(30)   NOT NULL,
  middle_name      VARCHAR(30),
  last_name        VARCHAR(30),
  email            VARCHAR(50)   NOT NULL,
  mobile_phone     VARCHAR(20)   NOT NULL,
  domain           VARCHAR(255)  NOT NULL,
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

INSERT INTO "user"."user"(username, password, first_name, middle_name, last_name, email, mobile_phone, domain, status, created_at, created_by, updated_at, updated_by, deleted) VALUES 
('system-01', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 01', 'Middle 01', 'Last 01', 'system01@cranium.id', '08111111111', 'admin', 1, now(), 1, now(), 1, false),
('system-02', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 02', 'Middle 02', 'Last 02', 'system02@cranium.id', '08122222222', 'admin', 1, now(), 1, now(), 1, false),
('system-03', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 03', 'Middle 03', 'Last 03', 'system03@cranium.id', '08133333333', 'admin', 1, now(), 1, now(), 1, false),
('system-04', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 04', 'Middle 04', 'Last 04', 'system04@cranium.id', '08144444444', 'admin', 1, now(), 1, now(), 1, false),
('system-05', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 05', 'Middle 05', 'Last 05', 'system05@cranium.id', '08155555555', 'admin', 1, now(), 1, now(), 1, false),
('system-06', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 06', 'Middle 06', 'Last 06', 'system06@cranium.id', '08166666666', 'admin', 1, now(), 1, now(), 1, false),
('system-07', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 07', 'Middle 07', 'Last 07', 'system07@cranium.id', '08177777777', 'admin', 1, now(), 1, now(), 1, false),
('system-08', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'System 08', 'Middle 08', 'Last 08', 'system08@cranium.id', '08188888888', 'admin', 1, now(), 1, now(), 1, false),
('scheduler-09', '$2a$10$/FZ4NIwCq6Ou0UXSQNazDuORD7vPbdL53pB3YDFC7AaA6D3kxlJeW', 'Scheduler 09', 'Middle 09', 'Last 09', 'scheduler09@cranium.id', '08199999999', 'admin', 1, now(), 1, now(), 1, false),
('noauth-10', '$2a$10$qALDD4McCZ.DBwtRFJa24.Bqs7N2pddAvYQ7vx4NJ1cRTP.55n94e', 'Scheduler 10', 'Middle 10', 'Last 10', 'noauth10@cranium.id', '08100000000', 'admin', 1, now(), 1, now(), 1, false),
('admin-11', '$2a$10$RptTjZxRvTyBeU73prp7k.yXpbsHbqTDTstXsveLhEOdkZ5mwtIKe', 'Admin 11', 'Middle 11', 'Last 11', 'admin11@cranium.id', '08211111111', 'admin', 1, now(), 1, now(), 1, false),
('user-12', '$2a$10$qwUUR0kGyWUwt0PV7mfQNeOdiiq3IVXe8QMWmJfRH0f5JJFUxzrKe', 'First 12', 'Middle 12', 'Last 12', 'user12@cranium.id', '08311111111', '', 1, now(), 1, now(), 1, false),
('user-13', '$2a$10$D3VeGyGsTumKWMFvp.gPfunqYBzFbtXb83HzTXtxLaQVsaCGNoRce', 'First 13', 'Middle 13', 'Last 13', 'user13@cranium.id', '08322222222', '', 1, now(), 1, now(), 1, false),
('user-14', '$2a$10$kY9W/Qd6H8NlJIJC38d3HeP57Kpm9iKYuUeBNegYhLKe5KasCs0WO', 'First 14', 'Middle 14', 'Last 14', 'user14@cranium.id', '08333333333', '', 1, now(), 1, now(), 1, false);
