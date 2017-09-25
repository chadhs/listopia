-- src/listopia/item/sql/item.sql
-- listopia list item queries

-- :name create-item! :! :n
-- :doc insert a single list item
insert into items (name, description)
values (:name, :description)
returning id

-- :name update-item! :! :n
-- :doc update a single list item by item_id
update items
set checked = :checked
where id = :item-id

-- :name delete-item! :! :n
-- :doc delete a single list item by item_id
delete from items
where id = :item-id

-- :name read-items :? :*
-- :doc get all list items
select id, name, description, checked, date_created from items
order by date_created
