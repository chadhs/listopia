(ns listopia.home.route
  (:require [listopia.home.handler :as    home.handler])
  (:require [compojure.core        :refer [defroutes ANY GET POST PUT DELETE]]))


(defroutes home-routes
  (GET  "/home" [] home.handler/handle-home-index))
