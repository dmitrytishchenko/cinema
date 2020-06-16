create table if not exists account
(id serial primary key,
 fio text,
 phoneNumber integer,
 unique (phonenumber));
create table if not exists hall
(id serial primary key,
 rows integer ,
 columns integer,
 is_active integer,
 id_account integer,
 check (is_active <= 1),
 foreign key (id_account) references account(id));
