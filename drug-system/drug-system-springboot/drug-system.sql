use drug_system;
drop table if exists `user`;
--  这是用户表
create table `user` (
    `id` bigint(20) unsigned not null auto_increment comment '主键',
    `phone` varchar(11) character set utf8mb4 collate utf8mb4_general_ci null default '' comment '手机号码',
    `password` varchar(128) character set utf8mb4 collate utf8mb4_general_ci null default '' comment '密码',
    `email` varchar(100)  character set utf8mb4 collate utf8mb4_general_ci null default '' comment '邮箱',
    `nick_name` varchar(32) character set utf8mb4 collate utf8mb4_general_ci null default '' comment '昵称，默认为用户id',
    `icon` varchar(255) character set utf8mb4 collate utf8mb4_general_ci null default '' comment '头像',
    `gender` enum('男','女') not null default '男' comment '性别',
    `create_time` timestamp not null default current_timestamp comment'创建时间',
    `update_time` timestamp not null default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
    primary key(`id`) using btree,
    unique index `unique_key_phone`(`phone`) using btree,
    unique index `unique_key_email`(`email`) using btree
) engine = innodb auto_increment =1010 character set =utf8mb4 collate = utf8mb4_general_ci row_format=compact;
insert user(`phone`,`password`,`email`,`nick_name`,`gender`) values ('13134566789','123456','123456@qq.com','张三','男');

drop table if exists `doctor`;
create table `doctor`(
    id bigint(20) unsigned auto_increment comment'主键',
    name varchar(64) not null default '医师名称',
    username varchar(64) unique not null comment '医师账号',
    password varchar(255) not null comment '密码',
    phone varchar(11) character set utf8mb4 collate utf8mb4_general_ci null default '' comment '手机号码',
    age int null ,
    gender  enum('男','女') not null default '男' comment '性别',
    icon varchar(255) character set utf8mb4 collate utf8mb4_general_ci null default '' comment '头像',
    status tinyint not null default 0 comment '员工状态:0-禁用 1-启用 2-离职',
    create_user int not null,    -- 可能是自己注册，也可能是管理员帮忙操作
    update_user int not null,    -- 存的是user的id
    create_time datetime not null default now(),
    update_time datetime not null default now(),
    primary key (`id`) using btree,
    unique index `unique_key_phone`(`phone`) using btree,
    unique index `unique_key_username`(username) using btree
) ENGINE = InnoDB AUTO_INCREMENT = 1010 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

drop table if exists `admin`;
create table `admin`(
    id bigint(20) unsigned not null auto_increment comment'主键',
    username varchar(64) not null comment '管理员账号名',
    password varchar(255) not null comment '密码',
    phone varchar(11) character set  utf8mb4 collate utf8mb4_general_ci null default '' comment '手机号码',
    email varchar(100) character set utf8mb4 collate utf8mb4_general_ci null default '' comment '邮箱',
    create_time timestamp not null default current_timestamp comment '创建时间',
    update_time timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key(`id`) using btree,
    unique index `unique_key_username`(`username`) using btree,
    unique index `unique_key_phone`(`phone`) using btree,
    unique index `unique_key_email`(`email`) using btree
) engine = innodb auto_increment = 1010 character set =utf8mb4 collate =utf8mb4_general_ci row_format =compact comment='管理员表';
insert into admin(username,password,phone,email) values ('admin',123456,13134566789,'1234578@qq.com');