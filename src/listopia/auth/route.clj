(ns listopia.auth.route
  (:require [listopia.auth.handler :as    auth.handler])
  (:require [compojure.core        :refer [defroutes ANY GET POST PUT DELETE]]))


(defroutes auth-routes
  (POST "/auth/login"    [] auth.handler/handle-auth-login)
  (POST "/auth/register" [] auth.handler/handle-auth-register!))
