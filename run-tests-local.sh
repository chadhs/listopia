#!/bin/sh

[[ -n $(psql -l | grep listopia-test) ]] && dropdb listopia-test
createdb listopia-test
lein with-profile test migratus migrate && lein test
dropdb listopia-test
