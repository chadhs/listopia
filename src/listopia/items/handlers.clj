(ns listopia.items.handlers
  (:require [listopia.db :refer [database-url]]
            [listopia.items.model :as items.model]
            [listopia.items.view :as items.view]))

(defn handle-index-items [req]
  (let [db database-url
        items (items.model/read-items db)]
    {:status 200
     :headers {}
     :body (items.view/items-page items)}))

(defn handle-create-item! [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        db database-url
        item-id (items.model/create-item! db {:name name :description description})]
    {:status 302
     :headers {"Location" "/items"}
     :body ""}))

(defn handle-delete-item! [req]
  (let [db database-url
        item-id (java.util.UUID/fromString (:item-id (:route-params req)))
        exists? (items.model/delete-item! db {:item-id item-id})]
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
        exists? (items.model/update-item! db {:item-id item-id :checked (= "true" checked)})]
    (if exists?
      {:status 302
       :headers {"Location" "/items"}
       :body ""}
      {:status 404
       :body "Item not found."
       :headers {}})))
