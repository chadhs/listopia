-- src/listopia/auth/sql/list.sql
-- listopia auth queries


-- :name create-account! :? :n
-- :doc create a new account record, returning the id
insert into account (email, display_name, password_hash)
values (:email, :display-name, :password-hash)
returning id


-- :name auth-account:? :n
-- :doc fetch an account by email, returning the password hash
select id, email, password_hash from account
where email = :account-email


-- :name read-account:? :n
-- :doc fetch an account's info by account id
select id, email, display_name, date_created from account
where id = :account-id
