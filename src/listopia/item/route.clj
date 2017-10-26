(ns listopia.item.route
  (:require [listopia.item.handler :as    item.handler])
  (:require [compojure.core        :refer [defroutes ANY GET POST PUT DELETE]]))


(defroutes item-routes
  (POST "/item/create"          [] item.handler/handle-create-item!)
  (POST "/item/delete/:item-id" [] item.handler/handle-delete-item!)
  (POST "/item/update/:item-id" [] item.handler/handle-update-item!))
