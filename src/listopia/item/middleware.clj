(ns listopia.item.middleware
  (:require [listopia.item.model :refer [dburl]]))

(defn wrap-db [hdlr]
  (fn [req]
    (hdlr (assoc req :listopia/db dburl))))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "server"] "listopia")))
