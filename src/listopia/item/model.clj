(ns listopia.item.model
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "listopia/item/sql/item.sql")
