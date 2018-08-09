(ns listopia.home.handler
  (:require [listopia.home.view.index :as    home.view.index]
            [listopia.config          :refer [db-url]]
            [listopia.list.model      :as    list.model]
            [listopia.list.view.index :as    list.view.index]))


(defn handle-home-index [req]
  (let [user-id (get-in req [:session :identity])
        lists   (list.model/read-lists db-url {:user-id user-id})
        authorized? user-id]
    ;; default to the /lists page if logged in already
    (if-not authorized?
      (home.view.index/home-page)
      (list.view.index/lists-page lists))))
