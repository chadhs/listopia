(ns listopia.item.middleware
  (:require [listopia.item.model :refer [dburl]]))

(defn wrap-db [hdlr]
  (fn [req]
    (hdlr (assoc req :listopia/db dburl))))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "server"] "nomad")))

(defn wrap-simulated-methods [hdlr]
  (fn [req]
    (let [sim-methods {"PUT" :put
                       "DELETE" :delete}]
         (if-let [method (and (= :post (:request-method req))
                              (sim-methods (get-in req [:params "_method"])))]
           (hdlr (assoc req :request-method method))
           (hdlr req)))))
