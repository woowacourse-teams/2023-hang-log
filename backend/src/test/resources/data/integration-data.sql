SET referential_integrity FALSE;
INSERT INTO trip(title, image_name, start_date, end_date, description, created_at, modified_at, status)
VALUES ('test', '123.jpeg', '2023-08-01', '2023-08-07', 'description', now(), now(), 'USABLE');
INSERT INTO trip_city(trip_id, city_id, created_at, modified_at, status)
VALUES (1, 1, now(), now(), 'USABLE');
INSERT INTO trip_city(trip_id, city_id, created_at, modified_at, status)
VALUES (1, 2, now(), now(), 'USABLE');
INSERT INTO trip_city(trip_id, city_id, created_at, modified_at, status)
VALUES (1, 3, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, '0801', 1, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, '0802', 2, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, '0803', 3, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, '0804', 4, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, '0805', 5, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, '0806', 6, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, '0807', 7, now(), now(), 'USABLE');
INSERT INTO day_log(trip_id, title, ordinal, created_at, modified_at, status)
VALUES (1, 'etc', 8, now(), now(), 'USABLE');

-- day 1 1st usd food 10
INSERT INTO place(category_id, latitude, longitude, name, created_at, modified_at, status)
VALUES (264, 37.8701510000000, -122.2594606000000, '캘리포니아 대학교', now(), now(), 'USABLE');
INSERT INTO expense(category_id, currency, amount, created_at, modified_at, status)
VALUES (100, 'usd', 10, now(), now(), 'USABLE');
INSERT INTO item(day_log_id, expense_id, place_id, title, item_type, memo, ordinal, rating, created_at, modified_at,
                 status)
VALUES (1, 1, 1, '캘리포니아 대학 제목', 'SPOT', '캘리포니아 학식 메모', 1, 3.5, now(), now(), 'USABLE');
INSERT INTO image(item_id, `name`, created_at, modified_at, status)
VALUES (1, 'test1.jpeg', now(), now(), 'USABLE');

-- day 1 2nd usd culture 5
INSERT INTO place(category_id, latitude, longitude, name, created_at, modified_at, status)
VALUES (264, 33.6423814000000, -117.8416747000000, '캘리포니아, 어바인 대학', now(), now(), 'USABLE');
INSERT INTO expense(category_id, currency, amount, created_at, modified_at, status)
VALUES (200, 'usd', 5, now(), now(), 'USABLE');
INSERT INTO item(day_log_id, expense_id, place_id, title, item_type, memo, ordinal, rating, created_at, modified_at,
                 status)
VALUES (1, 2, 2, '캘리포니아 어바인 대학 제목', 'SPOT', '캘리포니아 어바인 메모', 2, 3.5, now(), now(), 'USABLE');
INSERT INTO image(item_id, `name`, created_at, modified_at, status)
VALUES (2, 'test2.jpeg', now(), now(), 'USABLE');

-- day 1 3nd usd shopping 100
INSERT INTO expense(category_id, currency, amount, created_at, modified_at, status)
VALUES (300, 'usd', 100, now(), now(), 'USABLE');
INSERT INTO item(day_log_id, expense_id, place_id, title, item_type, memo, ordinal, rating, created_at, modified_at,
                 status)
VALUES (1, 3, null, '캘리포니아 어바인 쇼핑 제목', 'SPOT', '캘리포니아 어바인 쇼핑 메모', 3, 4.5, now(), now(), 'USABLE');
INSERT INTO image(item_id, `name`, created_at, modified_at, status)
VALUES (3, 'test3.jpeg', now(), now(), 'USABLE');


-- day 2 jpy accommodation 1000
INSERT INTO expense(category_id, currency, amount, created_at, modified_at, status)
VALUES (400, 'jpy', 1000, now(), now(), 'USABLE');
INSERT INTO item(day_log_id, expense_id, place_id, title, item_type, memo, ordinal, rating, created_at, modified_at,
                 status)
VALUES (2, 4, null, '일본', 'NON_SPOT', '일식 식사', 1, 2.5, now(), now(), 'USABLE');

-- day 3 cny transportation 200
INSERT INTO expense(category_id, currency, amount, created_at, modified_at, status)
VALUES (500, 'cny', 200, now(), now(), 'USABLE');
INSERT INTO item(day_log_id, expense_id, place_id, title, item_type, memo, ordinal, rating, created_at, modified_at,
                 status)
VALUES (3, 5, null, '중국', 'NON_SPOT', '중국 택시', 1, 2.5, now(), now(), 'USABLE');

-- day 4 eur etc 20
INSERT INTO expense(category_id, currency, amount, created_at, modified_at, status)
VALUES (600, 'eur', 200, now(), now(), 'USABLE');
INSERT INTO item(day_log_id, expense_id, place_id, title, item_type, memo, ordinal, rating, created_at, modified_at,
                 status)
VALUES (4, 6, null, '유럽', 'NON_SPOT', '유우럽', 1, 2.5, now(), now(), 'USABLE');
SET referential_integrity TRUE;
