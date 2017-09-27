drop index items_list_id_idx;
--;
alter table items
rename column item_id to id;
--;
alter table lists
rename column list_id to id;
--;
create index on items(id);
