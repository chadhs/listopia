(ns listopia.list.model
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "listopia/list/sql/list.sql")
