(ns listopia.lists.handlers
  (:require [listopia.db :refer [database-url]]
            [listopia.lists.model :refer [create-list!
                                          read-lists
                                          delete-list!]]
            [listopia.lists.view :refer [lists-page]]))

(defn handle-index-lists [req]
  (let [db database-url
        lists (read-lists db)]
    {:status 200
     :headers {}
     :body (lists-page lists)}))

(defn handle-create-list [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        db database-url
        list-id (create-list! db {:name name :description description})]
    {:status 302
     :headers {"Location" "/lists"}
     :body ""}))

(defn handle-delete-list [req]
  (let [db database-url
        list-id (java.util.UUID/fromString (:list-id (:route-params req)))
        exists? (delete-list! db {:list-id list-id})]
    (if exists?
      {:status 302
       :headers {"Location" "/lists"}
       :body ""}
      {:status 404
       :body "List not found."
       :headers {}})))
