-- users
insert into book_user(user_id,user_pass,user_phone_number) values ('user1','1234','000-1111-2222');
insert into book_user(user_id,user_pass,user_phone_number) values ('user2','1234','000-3333-4444');

-- book1
insert into book_use_status(book_seq,user_id,borrow_start,borrow_end) values(3,'user1','2023-6-1','2023-6-14');
update book_user set max_book = max_book - 1 where user_id = 'user1';
update book_copy set book_position = 'BB-user1' where book_seq = 3;

-- book2
insert into book_use_status(book_seq,user_id,borrow_start,borrow_end) values(10,'user1','2023-6-5','2023-6-18');
update book_user set max_book = max_book - 1 where user_id = 'user1';
update book_copy set book_position = 'BB-user1' where book_seq = 10;

-- book3
insert into book_use_status(book_seq,user_id,borrow_start,borrow_end) values(16,'user1','2023-6-5','2023-6-18');
update book_user set max_book = max_book - 1 where user_id = 'user1';
update book_copy set book_position = 'BB-user1' where book_seq = 16;

-- book 3 return
update book_use_status set return_date = '2023-6-14' where user_id = 'user1' and book_seq = 16;
update book_user set max_book = max_book + 1 where user_id = 'user1';
update book_copy set book_position = 'BS-0001' where book_seq = 16;

