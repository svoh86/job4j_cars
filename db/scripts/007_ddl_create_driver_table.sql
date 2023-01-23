CREATE TABLE IF NOT EXISTS driver (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  user_id INT NOT NULL REFERENCES auto_user(id)
);

comment on table driver is 'Владелец';
comment on column driver.id is 'Идентификатор владелеца';
comment on column driver.name is 'Имя владелеца';
comment on column driver.user_id is 'Внешний ключ на пользователя';