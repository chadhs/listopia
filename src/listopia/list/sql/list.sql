-- src/listopia/list/sql/list.sql
-- listopia list queries

-- :name create-list! :! :n
-- :doc insert a single list
insert into list (name, description)
values (:name, :description)
returning id

-- :name delete-list! :! :n
-- :doc delete a single list by list_id
delete from list
where id = :list-id

-- :name read-lists :? :*
-- :doc get all lists
select id, name, description, date_created from list
order by date_created