# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table doctors (
  id                            bigint auto_increment not null,
  name                          varchar(255) not null,
  address1                      varchar(255) not null,
  address2                      varchar(255) not null,
  pincode                       varchar(255) not null,
  state                         varchar(255) not null,
  country                       varchar(255) not null,
  daytimephonenumber            varchar(255) not null,
  offtimephonenumber            varchar(255) not null,
  email                         varchar(255) not null,
  encryptedpassword             varchar(255) not null,
  constraint pk_doctors primary key (id)
);

create table login_session (
  id                            bigint auto_increment not null,
  user_id                       bigint not null,
  token                         varchar(255) not null,
  create_at                     datetime not null,
  expire_at                     datetime not null,
  last_modified_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  ip_addresses                  varchar(255) not null,
  constraint pk_login_session primary key (id)
);

create table patients (
  id                            bigint auto_increment not null,
  name                          varchar(255) not null,
  dob                           varchar(255) not null,
  address1                      varchar(255) not null,
  address2                      varchar(255) not null,
  pincode                       varchar(255) not null,
  state                         varchar(255) not null,
  country                       varchar(255) not null,
  contactnumber1                varchar(255) not null,
  contactnumber2                varchar(255) not null,
  email                         varchar(255) not null,
  constraint pk_patients primary key (id)
);

create table practices (
  id                            bigint auto_increment not null,
  practicename                  varchar(255) not null,
  address1                      varchar(255) not null,
  address2                      varchar(255) not null,
  pincode                       varchar(255) not null,
  state                         varchar(255) not null,
  country                       varchar(255) not null,
  daytimephonenumber            varchar(255) not null,
  offtimephonenumber            varchar(255) not null,
  email                         varchar(255) not null,
  constraint pk_practices primary key (id)
);

create table practices_doctors (
  id                            bigint auto_increment not null,
  pid_id                        bigint not null,
  did_id                        bigint not null,
  constraint pk_practices_doctors primary key (id)
);

alter table practices_doctors add constraint fk_practices_doctors_pid_id foreign key (pid_id) references practices (id) on delete restrict on update restrict;
create index ix_practices_doctors_pid_id on practices_doctors (pid_id);

alter table practices_doctors add constraint fk_practices_doctors_did_id foreign key (did_id) references doctors (id) on delete restrict on update restrict;
create index ix_practices_doctors_did_id on practices_doctors (did_id);


# --- !Downs

alter table practices_doctors drop foreign key fk_practices_doctors_pid_id;
drop index ix_practices_doctors_pid_id on practices_doctors;

alter table practices_doctors drop foreign key fk_practices_doctors_did_id;
drop index ix_practices_doctors_did_id on practices_doctors;

drop table if exists doctors;

drop table if exists login_session;

drop table if exists patients;

drop table if exists practices;

drop table if exists practices_doctors;

