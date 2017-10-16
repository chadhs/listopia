(ns listopia.lists.handlers
  (:require [listopia.db :refer [database-url]]
            [listopia.lists.model :as lists.model]
            [listopia.lists.view :as lists.view]))

(defn handle-index-lists [req]
  (let [db database-url
        lists (lists.model/read-lists db)]
    {:status 200
     :headers {}
     :body (lists.view/lists-page lists)}))

(defn handle-create-list! [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        db database-url
        list-id (lists.model/create-list! db {:name name :description description})]
    {:status 302
     :headers {"Location" "/lists"}
     :body ""}))

(defn handle-delete-list! [req]
  (let [db database-url
        list-id (java.util.UUID/fromString (:list-id (:route-params req)))
        exists? (lists.model/delete-list! db {:list-id list-id})]
    (if exists?
      {:status 302
       :headers {"Location" "/lists"}
       :body ""}
      {:status 404
       :body "List not found."
       :headers {}})))
