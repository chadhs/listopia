create extension if not exists "uuid-ossp";
--;;
create table if not exists items (
id uuid primary key default uuid_generate_v4(),
name text not null,
description text not null,
checked boolean not null default false,
date_created timestamptz not null default now()
);
