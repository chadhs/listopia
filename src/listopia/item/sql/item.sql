-- src/listopia/item/sql/item.sql
-- listopia list item queries


-- :name create-item! :! :n
-- :doc insert a single list item
insert into item (name, description, list_id)
values (:name, :description, :list-id)
returning id


-- :name update-item! :! :n
-- :doc update a single list item by item_id
update item
set checked = :checked
where id = :item-id


-- :name delete-item! :! :n
-- :doc delete a single list item by item_id
delete from item
where id = :item-id


-- :name delete-list-items! :! :*
-- :doc delete all items associated with a single list
delete from item
where list_id = :list-id


-- :name read-items :? :*
-- :doc get all list items
select id, name, description, checked, date_created from item
order by date_created


-- :name read-list-items :? :*
-- :doc get all list items associated with a list
select id, name, description, checked, date_created from item
where list_id = :list-id
order by date_created
