app_userdrop database herec_uit;
create database herec_uit;
use herec_uit;

ALTER DATABASE herec_uit CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create table app_user (
	id int auto_increment primary key,
    phone_number varchar(10) unique not null,
    fullname text,
    address text,
    dob datetime,
    password text not null,
    hyperledger_name text not null,
    id_role int not null
);

create table organization (
	id int auto_increment primary key,
    name text,
    hyperledger_name text not null
);

create table app_role (
	id int auto_increment primary key,
    role varchar(100) not null unique
);

create table allergy (
	id int auto_increment primary key,
    name nvarchar(100),
    status nvarchar(100),
    reaction nvarchar(100),
    id_diagnosis int
);

create table medication (
	id int auto_increment primary key,
    quantity int,
    dose_quantity text,
    note text,
    name text,
    start_date date,
    end_date date,
    id_diagnosis int
);

create table diagnosis (
	id int auto_increment primary key,
    clinician nvarchar(100),
    created_at datetime,
    symtoms text,
	id_user int, 
    id_org int
);

create table permission (
	id_user int, 
    id_org int,
    primary key(id_user, id_org)
);

create table appointment (
	id int auto_increment primary key,
    created_at datetime,
    appointment_time datetime,
    id_user int,
    id_org int
);

--
alter table app_user add foreign key (id_role) references app_role(id);
alter table diagnosis add foreign key (id_user) references app_user(id);
alter table diagnosis add foreign key (id_org) references organization(id);
alter table allergy add foreign key (id_diagnosis) references diagnosis(id);
alter table medication add foreign key (id_diagnosis) references diagnosis(id);
alter table permission add foreign key (id_user) references app_user(id);
alter table permission add foreign key (id_org) references organization(id);

alter table appointment add foreign key (id_user) references app_user(id);
alter table appointment add foreign key (id_org) references organization(id);

--
insert into app_role (role)values ("ROLE_PATIENT"), ("ROLE_DOCTER");

insert into organization(name, hyperledger_name) values ("Bệnh viện Quận 12", 'quan12'), ("Bệnh viện Quận Tân Phú", 'tanphu');


