# listopia [![CircleCI](https://circleci.com/gh/chadhs/listopia/tree/master.svg?style=svg)](https://circleci.com/gh/chadhs/listopia/tree/master)

an exploration of web development in Clojure.

## usage

make lists!

## local dev setup

listopia uses postgresql; you'll need this availalbe for local dev.  to create a local dev database run the following command.

```sh
createdb listopia-dev
```

create a `profiles.clj` in the project root (which is ignored by git) to define your local dev database connection.  an example would look like this:

```clojure
{:dev {:env {:database-url "jdbc:postgresql://localhost/listopia-dev"}}}
{:dev
 {:env
  {:database-url "jdbc:postgresql://localhost/listopia-dev"
   :reported-log-level "debug"}}
 :test
 {:env
  {:database-url "jdbc:postgresql://localhost/listopia-test"
   :reported-log-level "debug"}}}
```

to run locally use `lein run` this will start on port 8000 by default, but accepts a port number as an argument as well.

## database migrations

- the clojure migratus library is used to handle db schema changes.
- migrations are located in the `resources/migrations` folder.
- to create a new migration `lein migratus create <descriptive-name>` and then edit the `up` and `down` files created.
- to apply migrations to your local dev instance `lein migratus migrate`
- production migrations are applied automatically; see `circle.yml`


## production setup

you'll need to load the following environment variables in your production environment:

- `DATABASE_URL`
- `PORT`

optionally you can customize the following variables in your production environment:

- `REPORTED_LOG_LEVEL` (defaults to "warn"; set's min level to write to production log)
- `LOG_APPENDER` (options "println" "sentry")
- `SENTRY_DSN` (required only if you've set log-appender to "sentry")

## license

Copyright © Chad Stovern

Distributed under the BSD 3-Clause License
