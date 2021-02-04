create table if not exists account
(id serial primary key,
 fio text,
 phoneNumber varchar (20),
 unique (phonenumber));
create table if not exists hall
(id serial,
 rows integer ,
 columns integer,
 is_active integer,
 id_account integer,
 primary key (id, rows, columns),
 foreign key (id_account) references account(id));

