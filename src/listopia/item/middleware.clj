(ns listopia.item.middleware
  (:require [listopia.item.model :refer [database-url]])
  (:require [environ.core :refer [env]]))

(defn wrap-db [hdlr]
  (fn [req]
    (hdlr (assoc req :listopia/db database-url))))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "server"] "listopia")))
