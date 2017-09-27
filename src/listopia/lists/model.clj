(ns listopia.lists.model
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "listopia/lists/sql/lists.sql")
