#!/bin/sh

dropdb listopia-test
createdb listopia-test
lein with-profile test migratus migrate && lein test
