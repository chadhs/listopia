(ns listopia.db
  (:require [environ.core :refer [env]]))

(def database-url
  (env :database-url))
