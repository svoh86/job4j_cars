ALTER TABLE auto_post
ADD COLUMN price BIGINT;

comment on column auto_post.price is 'Цена автомобиля';