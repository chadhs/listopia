(ns listopia.route
  (:require [listopia.item.route   :as    item.route]
            [listopia.list.route   :as    list.route]
            [listopia.list.handler :as    list.handler])
  (:require [compojure.core        :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.core        :as    compojure]
            [compojure.route       :as    route]))


(defroutes core-routes
  (GET             "/" [] list.handler/handle-index-lists)
  (route/not-found        "Page not found."))


(def combined-routes
  ;; core-routes last so the not-found call is the last matching route
  (compojure/routes
   item.route/item-routes
   list.route/list-routes
   core-routes))
