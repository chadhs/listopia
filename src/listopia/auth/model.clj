(ns listopia.auth.model
  (:require [hugsql.core :as hugsql]))


(hugsql/def-db-fns "listopia/auth/sql/auth.sql")
