(ns listopia.items.route
  (:require [listopia.items.handler :refer [handle-index-items
                                            handle-create-item
                                            handle-delete-item
                                            handle-update-item]])
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]))

(defroutes routes
  (GET       "/"                      [] handle-index-items)
  (GET       "/items"                 [] handle-index-items)
  (POST      "/items"                 [] handle-create-item)
  (POST      "/items/delete/:item-id" [] handle-delete-item)
  (POST      "/items/update/:item-id" [] handle-update-item)
  (not-found                             "Page not found."))
