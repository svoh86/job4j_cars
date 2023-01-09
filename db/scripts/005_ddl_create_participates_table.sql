CREATE TABLE IF NOT EXISTS participates (
    id SERIAL PRIMARY KEY,
    user_id int NOT NULL REFERENCES auto_user(id),
    post_id int NOT NULL REFERENCES auto_post(id)
);

comment on table participates is 'Подписки';
comment on column participates.id is 'Идентификатор подписки';
comment on column participates.user_id is 'Внешний ключ на пользователя';
comment on column participates.post_id is 'Внешний ключ на объявление';