-- src/listopia/item/sql/item.sql
-- listopia list item queries

-- :name create-item! :! :n
-- :doc insert a single list item
insert into items (name, description)
values (:name, :description)
returning id

-- :name update-item! :! :n
-- :doc update a single list item by id
update items
set checked = :checked
where id = :id

-- :name delete-item! :! :n
-- :doc delete a single list item by id
delete from items
where id = :id

-- :name read-items :? :*
-- :doc get all list items
select id, name, description, checked, date_created from items
order by date_created
