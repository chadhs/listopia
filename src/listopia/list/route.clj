(ns listopia.list.route
  (:require [listopia.list.handler :as list.handler])
  (:require [compojure.core        :refer [defroutes ANY GET POST PUT DELETE]]))


(defroutes list-routes
  (GET  "/lists"                [] list.handler/handle-index-lists)
  (POST "/list/create"          [] list.handler/handle-create-list!)
  (GET  "/list/:list-id"        [] list.handler/handle-index-list)
  (POST "/list/delete/:list-id" [] list.handler/handle-delete-list!))
