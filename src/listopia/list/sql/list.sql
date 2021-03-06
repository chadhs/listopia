-- src/listopia/list/sql/list.sql
-- listopia list queries


-- :name create-list! :? :n
-- :doc insert a single list, returning the id, thus the ? in the name rather than ! for execute
insert into list (name, description, user_id)
values (:name, :description, :user-id)
returning id


-- :name delete-list! :! :n
-- :doc delete a single list by list_id
delete from list
where id = :list-id


-- :name read-lists :? :*
-- :doc get all lists
select id, name, description, date_created from list
where user_id = :user-id
order by date_created


-- :name read-list :? :n
-- :doc fetch a single list by list_id
select id, name, description, user_id, date_created from list
where id = :list-id and user_id = :user-id
