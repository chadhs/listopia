(ns listopia.routes
  (:require [listopia.items.routes   :refer [item-routes]]
            [listopia.lists.routes   :refer [list-routes]]
            [listopia.lists.handlers :refer [handle-index-lists]])
  (:require [compojure.core :refer [defroutes routes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]))

(defroutes core-routes
  (GET       "/" [] handle-index-lists)
  (not-found        "Page not found."))

(def all-routes
  ;; core-routes last so the not-found call is the last matching route
  (routes
   item-routes
   list-routes
   core-routes))
