create TABLE IF NOT EXISTS auto_post (
  id SERIAL PRIMARY KEY,
  text TEXT NOT NULL,
  created TIMESTAMP,
  auto_user_id INT NOT NULL REFERENCES auto_user(id)
);

comment on table auto_post is 'Объявления';
comment on column auto_post.id is 'Идентификатор объявления';
comment on column auto_post.text is 'Текст объявления';
comment on column auto_post.created is 'Дата создания объявления';
comment on column auto_post.auto_user_id is 'Внешний ключ на пользователя';