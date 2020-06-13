create table if not exists account
(id serial primary key,
 FIO text,
 phoneNumber integer);
create table if not exists hall
(id serial primary key,
 rows integer ,
 columns integer,
 is_active boolean);
