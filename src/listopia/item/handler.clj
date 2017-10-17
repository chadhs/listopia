(ns listopia.item.handler
  (:require [listopia.db :refer [database-url]]
            [listopia.item.model :as item.model]
            [listopia.item.view :as item.view]))

(defn handle-index-items [req]
  (let [db database-url
        items (item.model/read-items db)]
    {:status 200
     :headers {}
     :body (item.view/items-page items)}))

(defn handle-create-item! [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        db database-url
        item-id (item.model/create-item! db {:name name :description description})]
    {:status 302
     :headers {"Location" "/items"}
     :body ""}))

(defn handle-delete-item! [req]
  (let [db database-url
        item-id (java.util.UUID/fromString (:item-id (:route-params req)))
        exists? (item.model/delete-item! db {:item-id item-id})]
    (if exists?
      {:status 302
       :headers {"Location" "/items"}
       :body ""}
      {:status 404
       :body "Item not found."
       :headers {}})))

(defn handle-update-item! [req]
  (let [db database-url
        item-id (java.util.UUID/fromString (:item-id (:route-params req)))
        checked (get-in req [:params "checked"])
        exists? (item.model/update-item! db {:item-id item-id :checked (= "true" checked)})]
    (if exists?
      {:status 302
       :headers {"Location" "/items"}
       :body ""}
      {:status 404
       :body "Item not found."
       :headers {}})))
