-- src/listopia/auth/sql/list.sql
-- listopia auth queries


-- :name create-account! :? :n
-- :doc create a new account record, returning the id
insert into account (email, display_name, password_hash)
values (:email, :display-name, :password-hash)
returning id


-- :name read-account-auth-facts :? :n
-- :doc fetch an account's info by email, including password hash
select id, email, display_name, date_created, password_hash
from account
where email = :account-email


-- :name read-account-by-email :? :n
-- :doc fetch an account's info by email
select id, email, display_name, date_created
from account
where email = :account-email


-- :name read-account-by-id :? :n
-- :doc fetch an account's info by account id
select id, email, display_name, date_created
from account
where id = :account-id
