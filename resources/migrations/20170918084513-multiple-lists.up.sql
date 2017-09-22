create table lists (
list_id uuid primary key default uuid_generate_v4(),
name text not null,
description text not null,
date_created timestamptz not null default now()
);
--;
alter table items
rename column id to item_id;
--;
alter table items
add list_id uuid;
--;
create index on items(list_id);
