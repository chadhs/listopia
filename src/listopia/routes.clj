(ns listopia.routes
  (:require [listopia.items.routes   :as items.routes]
            [listopia.lists.routes   :as lists.routes]
            [listopia.lists.handlers :as lists.handlers])
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.core :as compojure]
            [compojure.route :as route]))

(defroutes core-routes
  (GET             "/" [] lists.handlers/handle-index-lists)
  (route/not-found        "Page not found."))

(def combined-routes
  ;; core-routes last so the not-found call is the last matching route
  (compojure/routes
   items.routes/items-routes
   lists.routes/lists-routes
   core-routes))
