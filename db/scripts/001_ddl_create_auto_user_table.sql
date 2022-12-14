create TABLE IF NOT EXISTS auto_user (
  id SERIAL PRIMARY KEY,
  login TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL
);

comment on table auto_user is 'Пользователи';
comment on column auto_user.id is 'Идентификатор пользователя';
comment on column auto_user.login is 'Логин пользователя';
comment on column auto_user.password is 'Пароль пользователя';