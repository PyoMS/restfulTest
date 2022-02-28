insert into user values(90001, sysdate(), 'user1', 'test1111', '701010-1111111');
insert into user values(90002, sysdate(), 'user2', 'test2222', '801010-1111111');
insert into user values(90003, sysdate(), 'user3', 'test3333', '901010-1111111');

insert into post values(10001, 'My post1', 90001);
insert into post values(10002, 'My post2', 90001);