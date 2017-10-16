(ns listopia.lists.routes
  (:require [listopia.lists.handlers :as lists.handlers])
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]))

(defroutes lists-routes
  (GET  "/lists"                 [] lists.handlers/handle-index-lists)
  (POST "/lists"                 [] lists.handlers/handle-create-list!)
  (POST "/lists/delete/:list-id" [] lists.handlers/handle-delete-list!))










