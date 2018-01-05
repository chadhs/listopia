(ns listopia.item.handler
  (:require [listopia.config     :refer [db-url]]
            [listopia.item.model :as    item.model])
  (:require [ring.util.response  :as    response]))


(defn handle-create-item! [req]
  (let [name        (get-in req [:params :name])
        description (get-in req [:params :description])
        list-id     (java.util.UUID/fromString (get-in req [:params :list-id]))
        item-id     (item.model/create-item!
                     db-url
                     {:name        name
                      :description description
                      :list-id     list-id})]
    (response/redirect (str "/list/" list-id))))


(defn handle-delete-item! [req]
  (let [item-id (java.util.UUID/fromString (:item-id (:route-params req)))
        list-id (java.util.UUID/fromString (get-in req [:params :list-id]))
        exists? (item.model/delete-item! db-url {:item-id item-id})]
    (if exists?
      (response/redirect (str "/list/" list-id))
      (response/not-found "Item not found."))))


(defn handle-update-item! [req]
  (let [item-id (java.util.UUID/fromString (:item-id (:route-params req)))
        list-id (java.util.UUID/fromString (get-in req [:params :list-id]))
        checked (get-in req [:params :checked])
        exists? (item.model/update-item! db-url {:item-id item-id :checked (= "true" checked)})]
    (if exists?
      (response/redirect (str "/list/" list-id))
      (response/not-found "Item not found."))))
