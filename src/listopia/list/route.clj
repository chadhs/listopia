(ns listopia.list.route
  (:require [listopia.list.handler :as list.handler])
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]))

(defroutes list-routes
  (GET  "/lists"                 [] list.handler/handle-index-lists)
  (POST "/lists"                 [] list.handler/handle-create-list!)
  (POST "/lists/delete/:list-id" [] list.handler/handle-delete-list!))
