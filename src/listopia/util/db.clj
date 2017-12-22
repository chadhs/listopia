(ns listopia.util.db
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "listopia/util/sql/util.sql")
