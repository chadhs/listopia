(ns listopia.list.handler
  (:require [listopia.config          :refer [db-url]]
            [listopia.list.model      :as    list.model]
            [listopia.item.model      :as    item.model]
            [listopia.list.view.index :as    list.view.index]
            [listopia.list.view.list  :as    list.view.list]
            [listopia.util.core       :as    util])
  (:require [ring.util.response       :as    response]
            [taoensso.timbre          :as    timbre]))


(defn handle-index-lists [req]
  (let [user-id (get-in req [:session :user-id])
        lists   (list.model/read-lists db-url {:user-id user-id})]
    (list.view.index/lists-page lists)))


(defn handle-index-list [req]
  (let [list-id    (java.util.UUID/fromString (:list-id (:route-params req)))
        list       (list.model/read-list db-url {:list-id list-id})
        list-items (item.model/read-list-items db-url {:list-id list-id})]
    (list.view.list/list-page {:list       list
                               :list-id    list-id
                               :list-items list-items})))


(defn handle-create-list! [req]
  (let [user-id     (get-in req [:session :user-id])
        name        (get-in req [:params :name])
        description (get-in req [:params :description])
        list-id     (list.model/create-list! db-url {:name name :description description :user-id user-id})]
    (timbre/info (str "list created: " (util/uuid->str list-id)))
    (response/redirect "/lists")))


(defn handle-delete-list! [req]
  (let [list-id (java.util.UUID/fromString (:list-id (:route-params req)))
        exists? (do
                  (item.model/delete-list-items! db-url {:list-id list-id})
                  (list.model/delete-list! db-url {:list-id list-id}))]
    (if exists?
      (do
        (timbre/info (str "list deleted: " list-id))
        (response/redirect "/lists"))
      (do
        (timbre/error (str "list delete failed list id not found: " list-id))
        (response/not-found "List not found.")))))
