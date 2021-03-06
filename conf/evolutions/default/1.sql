# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table careplan_in_json (
  id                            bigint auto_increment not null,
  medication_name               varchar(1000) not null,
  patientid                     bigint not null,
  constraint pk_careplan_in_json primary key (id)
);

create table careplans (
  id                            bigint auto_increment not null,
  did_id                        bigint,
  pid_id                        bigint,
  mname                         varchar(255),
  mcode                         varchar(255),
  msystemcode                   varchar(255),
  msystemcodename               varchar(255),
  rname                         varchar(255),
  rcode                         varchar(255),
  rsystemcode                   varchar(255),
  rsystemcodename               varchar(255),
  funits                        varchar(255),
  period                        varchar(255),
  dunits                        varchar(255),
  quantity                      varchar(255),
  startdate                     varchar(255),
  enddate                       varchar(255),
  update_at                     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  constraint pk_careplans primary key (id)
);

create table doctors (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  address1                      varchar(255),
  address2                      varchar(255),
  pincode                       varchar(255),
  state                         varchar(255),
  country                       varchar(255),
  daytimephonenumber            varchar(255),
  offtimephonenumber            varchar(255),
  email                         varchar(255),
  encryptedpassword             varchar(255),
  token                         varchar(255),
  activate                      tinyint(1) default 0,
  constraint pk_doctors primary key (id)
);

create table login_session (
  id                            bigint auto_increment not null,
  user_id                       bigint not null,
  token                         varchar(255) not null,
  create_at                     datetime not null,
  expire_at                     datetime not null,
  last_modified_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
  expired                       tinyint(1) default 0 not null,
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

create table question (
  id                            bigint auto_increment not null,
  question_number               bigint,
  did_id                        bigint,
  pid_id                        bigint,
  question_type                 varchar(255),
  question                      varchar(255),
  depend_on_previous_question   varchar(255),
  previous_question_answer_should_be varchar(255),
  choices                       varchar(255),
  constraint pk_question primary key (id)
);

alter table careplans add constraint fk_careplans_did_id foreign key (did_id) references doctors (id) on delete restrict on update restrict;
create index ix_careplans_did_id on careplans (did_id);

alter table careplans add constraint fk_careplans_pid_id foreign key (pid_id) references patients (id) on delete restrict on update restrict;
create index ix_careplans_pid_id on careplans (pid_id);

alter table practices_doctors add constraint fk_practices_doctors_pid_id foreign key (pid_id) references practices (id) on delete restrict on update restrict;
create index ix_practices_doctors_pid_id on practices_doctors (pid_id);

alter table practices_doctors add constraint fk_practices_doctors_did_id foreign key (did_id) references doctors (id) on delete restrict on update restrict;
create index ix_practices_doctors_did_id on practices_doctors (did_id);

alter table question add constraint fk_question_did_id foreign key (did_id) references doctors (id) on delete restrict on update restrict;
create index ix_question_did_id on question (did_id);

alter table question add constraint fk_question_pid_id foreign key (pid_id) references patients (id) on delete restrict on update restrict;
create index ix_question_pid_id on question (pid_id);


# --- !Downs

alter table careplans drop foreign key fk_careplans_did_id;
drop index ix_careplans_did_id on careplans;

alter table careplans drop foreign key fk_careplans_pid_id;
drop index ix_careplans_pid_id on careplans;

alter table practices_doctors drop foreign key fk_practices_doctors_pid_id;
drop index ix_practices_doctors_pid_id on practices_doctors;

alter table practices_doctors drop foreign key fk_practices_doctors_did_id;
drop index ix_practices_doctors_did_id on practices_doctors;

alter table question drop foreign key fk_question_did_id;
drop index ix_question_did_id on question;

alter table question drop foreign key fk_question_pid_id;
drop index ix_question_pid_id on question;

drop table if exists careplan_in_json;

drop table if exists careplans;

drop table if exists doctors;

drop table if exists login_session;

drop table if exists patients;

drop table if exists practices;

drop table if exists practices_doctors;

drop table if exists question;

