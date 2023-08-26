create table if not exists member (
member_id bigint not null auto_increment,
name varchar(100) not null,
email varchar(100),
phone varchar(13),
primary key (member_id)
);

create table if not exists category (
category_id bigint not null auto_increment,
category_name varchar(100),
primary key (category_id)
);

create table if not exists notice (
notice_id bigint not null auto_increment,
title varchar(100) not null,
content varchar(3000),
attach_file varchar(1000),
member_id bigint,
category_id bigint,
created_at datetime not null,
modified_at datetime,
primary key (notice_id),
foreign key (member_id) references member(member_id),
foreign key (category_id) references category(category_id)
);