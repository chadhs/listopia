(ns listopia.auth.route
  (:require [listopia.auth.handler :as    auth.handler])
  (:require [compojure.core        :refer [defroutes ANY GET POST PUT DELETE]]))


(defroutes auth-routes
  (GET  "/login"              [& error] auth.handler/handle-auth-login-page)
  (POST "/login"              []        auth.handler/handle-auth-login)
  (GET  "/register"           [& error] auth.handler/handle-auth-register-page)
  (POST "/register"           []        auth.handler/handle-auth-register!))
