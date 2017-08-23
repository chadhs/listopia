# listopia 

build/ci status: [![CircleCI](https://circleci.com/gh/chadhs/listopia/tree/master.svg?style=svg)](https://circleci.com/gh/chadhs/listopia/tree/master)

an exploration of web development in Clojure.

## usage

Make lists!

## local dev setup

listopia uses postgresql; you'll need this availalbe for local dev.  to create a local dev database run the following command.

```sh
createdb listopia-dev
```

create a `profiles.clj` in the project root (which is ignored by git) to define your local dev database connection.  an example would look like this:

```clojure
{:dev {:env {:database-url "jdbc:postgresql://localhost/listopia-dev"}}}
```

to run locally use `lein run` this will by default start on port 8000, but accepts a port number as an argument as well.

## production setup

you'll need to load an two environment variables in your production environment:

`DATABASE_URL` and `PORT`

## license

Copyright © 2016 Chad Stovern

Distributed under the Eclipse Public License either version 1.0
