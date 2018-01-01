(ns listopia.db
  (:require [environ.core :as environ]))


(def db-url
  (environ/env :database-url))
