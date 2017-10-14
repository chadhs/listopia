(ns listopia.lists.routes
  (:require [listopia.lists.handlers :refer [handle-index-lists
                                             handle-create-list
                                             handle-delete-list]])
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]))

(defroutes list-routes
  (GET       "/lists"                 [] handle-index-lists)
  (POST      "/lists"                 [] handle-create-list)
  (POST      "/lists/delete/:list-id" [] handle-delete-list))
