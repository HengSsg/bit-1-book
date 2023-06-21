use bitedu;
drop table book_user;
create table book_user(
	user_seq integer unique auto_increment,
	user_id varchar(10) primary key,
    user_pass varchar(10) not null,
    user_phone_number varchar(20) not null,
    user_status char(2) default '00', -- 정상
    user_grade char(1) default '1',
    max_book integer(2) default 3,
    service_stop date null 
);

drop table book_info;
create table book_info(
	book_isbn varchar(12) primary key,
    book_title varchar(200) not null,
    book_author varchar(120) not null,
    book_published_date date not null
);

drop table book_copy;
create table book_copy(
	book_seq integer primary key auto_increment,
    book_position varchar(20) not null default 'BS-0001',
    book_status varchar(20) not null default 'BM-0001', -- 책의현재 상태 - 시간,폐기, 수선, 보관, etc
    book_isbn varchar(12),
    constraint foreign key (book_isbn) references book_info (book_isbn) on delete set null
);
drop table book_use_status;
create table book_use_status(
	book_seq integer not null,
    user_id varchar(10) not null,
    borrow_start datetime not null,
    borrow_end datetime not null,
    return_date datetime null,
    primary key(book_seq,user_id),
    constraint foreign key (book_seq) references book_copy (book_seq) on delete cascade,
    constraint foreign key (user_id) references book_user (user_id) on delete cascade
);
