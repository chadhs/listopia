(ns listopia.items.routes
  (:require [listopia.items.handlers :as items.handlers])
  (:require [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]))

(defroutes items-routes
  (GET  "/items"                 [] items.handlers/handle-index-items)
  (POST "/items"                 [] items.handlers/handle-create-item!)
  (POST "/items/delete/:item-id" [] items.handlers/handle-delete-item!)
  (POST "/items/update/:item-id" [] items.handlers/handle-update-item!))
