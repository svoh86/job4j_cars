CREATE TABLE IF NOT EXISTS car (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  engine_id INT NOT NULL REFERENCES engine(id),
  driver_id INT NOT NULL REFERENCES driver(id)
);

comment on table car is 'Автомобиль';
comment on column car.id is 'Идентификатор автомобиля';
comment on column car.name is 'Марка автомобиля';
comment on column car.engine_id is 'Внешний ключ на двигатель';
comment on column car.driver_id is 'Внешний ключ на владельца';