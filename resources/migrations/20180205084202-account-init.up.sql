create table if not exists account (
id uuid primary key default uuid_generate_v4(),
email varchar(64) unique not null,
display_name varchar(64) not null,
password_hash varchar(128) not null,
date_created timestamptz not null default now()
);
--;
alter table list
add user_id uuid;
--;
create index on list(user_id);
