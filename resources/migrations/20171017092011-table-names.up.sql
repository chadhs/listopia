alter table items
rename to item;
--;
alter table lists
rename to list;
--;
alter table list
drop constraint lists_pkey;
--;
alter table item
drop constraint items_pkey;
--;
alter table list
add primary key (id);
--;
alter table item
add primary key (id);
--;
drop index items_id_idx;
--;
create index on item(list_id);
