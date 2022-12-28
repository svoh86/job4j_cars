create TABLE IF NOT EXISTS price_history (
  id SERIAL PRIMARY KEY,
  before BIGINT NOT NULL,
  after BIGINT NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
  auto_post_id int REFERENCES auto_post(id)
);

comment on table price_history is 'История цены объявления';
comment on column price_history.before is 'Цена до';
comment on column price_history.after is 'Цена после';
comment on column price_history.created is 'Дата';
comment on column price_history.auto_post_id is 'Внешний ключ на объявление';