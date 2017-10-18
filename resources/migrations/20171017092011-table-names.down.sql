alter table list
rename to lists;
--;
alter table item
rename to items;
--;
alter table lists
drop constraint list_pkey;
--;
alter table items
drop constraint item_pkey;
--;
alter table lists
add primary key (id);
--;
alter table items
add primary key (id);
--;
drop index item_list_id_idx;
--;
create index on items(id);
