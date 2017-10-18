(ns listopia.db
  (:require [environ.core :as environ]))


(def database-url
  (environ/env :database-url))
