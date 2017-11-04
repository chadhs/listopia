(ns listopia.item.handler
  (:require [listopia.db         :refer [database-url]]
            [listopia.item.model :as item.model])
  (:require [ring.util.response       :as    response]))


(defn handle-create-item! [req]
  (let [name        (get-in req [:params :name])
        description (get-in req [:params :description])
        list-id     (java.util.UUID/fromString (get-in req [:params :list-id]))
        db          database-url
        item-id     (item.model/create-item! 
                     db 
                     {:name        name 
                      :description description 
                      :list-id     list-id})]
    (response/redirect (str "/list/" list-id))))


(defn handle-delete-item! [req]
  (let [db      database-url
        item-id (java.util.UUID/fromString (:item-id (:route-params req)))
        list-id (java.util.UUID/fromString (get-in req [:params :list-id]))
        exists? (item.model/delete-item! db {:item-id item-id})]
    (if exists?
      (response/redirect (str "/list/" list-id))
      (response/not-found "Item not found."))))


(defn handle-update-item! [req]
  (let [db      database-url
        item-id (java.util.UUID/fromString (:item-id (:route-params req)))
        list-id (java.util.UUID/fromString (get-in req [:params :list-id]))
        checked (get-in req [:params :checked])
        exists? (item.model/update-item! db {:item-id item-id :checked (= "true" checked)})]
    (if exists?
      (response/redirect (str "/list/" list-id))
      (response/not-found "Item not found."))))
