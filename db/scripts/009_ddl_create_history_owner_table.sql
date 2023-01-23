CREATE TABLE IF NOT EXISTS history_owner (
  id SERIAL PRIMARY KEY,
  car_id INT NOT NULL REFERENCES car(id),
  driver_id INT NOT NULL REFERENCES driver(id),
  startAt TIMESTAMP WITHOUT TIME ZONE,
  endAt TIMESTAMP WITHOUT TIME ZONE
);

comment on table history_owner is 'История владения автомобилем';
comment on column history_owner.id is 'Идентификатор истории';
comment on column history_owner.car_id is 'Внешний ключ на автомобиль';
comment on column history_owner.driver_id is 'Внешний ключ на владельца';
comment on column history_owner.startAt is 'Дата начала вледения';
comment on column history_owner.endAt is 'Дата окончания вледения';