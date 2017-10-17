(ns listopia.item.route
  (:require [listopia.item.handler :as item.handler])
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]))

(defroutes item-routes
  (GET  "/items"                 [] item.handler/handle-index-items)
  (POST "/items"                 [] item.handler/handle-create-item!)
  (POST "/items/delete/:item-id" [] item.handler/handle-delete-item!)
  (POST "/items/update/:item-id" [] item.handler/handle-update-item!))
