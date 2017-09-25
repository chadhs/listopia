drop index items_id_idx;
--;
alter table items
rename column id to item_id;
--;
alter table lists
rename column id to list_id;
--;
create index on items(list_id);
