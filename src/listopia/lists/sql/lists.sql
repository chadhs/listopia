-- src/listopia/lists/sql/lists.sql
-- listopia list queries

-- :name create-list! :! :n
-- :doc insert a single list
insert into lists (name, description)
values (:name, :description)
returning id

-- :name delete-list! :! :n
-- :doc delete a single list by list_id
delete from lists
where id = :list-id

-- :name read-lists:? :*
-- :doc get all lists
select id, name, description, checked, date_created from lists
order by date_created
