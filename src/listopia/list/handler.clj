(ns listopia.list.handler
  (:require [listopia.db              :refer [database-url]]
            [listopia.list.model      :as    list.model]
            [listopia.item.model      :as    item.model]
            [listopia.list.view.index :as    list.view.index]
            [listopia.list.view.list  :as    list.view.list]))


(defn handle-index-lists [req]
  (let [db    database-url
        lists (list.model/read-lists db)]
    {:status  200
     :headers {}
     :body    (list.view.index/lists-page lists)}))


(defn handle-index-list [req]
  (let [db         database-url
        list-id    (java.util.UUID/fromString (:list-id (:route-params req)))
        list       (list.model/read-list db {:list-id list-id})
        list-items (item.model/read-list-items db {:list-id list-id})]
    {:status  200
     :headers {}
     :body    (list.view.list/list-page list list-id list-items)}))


(defn handle-create-list! [req]
  (let [name        (get-in req [:params "name"])
        description (get-in req [:params "description"])
        db          database-url
        list-id     (list.model/create-list! db {:name name :description description})]
    {:status  302
     :headers {"Location" "/lists"}
     :body    ""}))


(defn handle-delete-list! [req]
  (let [db      database-url
        list-id (java.util.UUID/fromString (:list-id (:route-params req)))
        exists? (do
                  (item.model/delete-list-items! db {:list-id list-id})
                  (list.model/delete-list! db {:list-id list-id}))]
    (if exists?
      {:status  302
       :headers {"Location" "/lists"}
       :body    ""}
      {:status  404
       :body    "List not found."
       :headers {}})))
