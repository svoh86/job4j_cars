ALTER TABLE auto_post
ADD COLUMN status BOOLEAN;

comment on column auto_post.status is 'Статус объявления';