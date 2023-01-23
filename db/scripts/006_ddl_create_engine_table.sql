CREATE TABLE IF NOT EXISTS engine (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL
);

comment on table engine is 'Двигатель';
comment on column engine.id is 'Идентификатор двигателя';
comment on column engine.name is 'Название двигателя';