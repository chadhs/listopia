(ns listopia.items.model
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "listopia/items/sql/items.sql")
