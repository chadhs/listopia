(ns listopia.item.middleware
  (:require [listopia.db :refer [database-url]]))

(defn wrap-db [hdlr]
  (fn [req]
    (hdlr (assoc req :listopia/db database-url))))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "server"] "listopia")))
