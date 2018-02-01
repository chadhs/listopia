(ns listopia.auth.handler
  (:require [listopia.config          :refer [db-url]]
            ;; [listopia.auth.model      :as    auth.model]
            [listopia.util.core       :as    util])
  (:require [ring.util.response       :as    response]
            [taoensso.timbre          :as    timbre]))


(defn handle-auth-register! [req]
  (let [email       (get-in req [:params :email])
        password    (get-in req [:params :password])
        ;; user-id     (auth.model/create-user! db-url {:email email :password password})
        ]
    ;; (timbre/info (str "user created: " (util/uuid->str user-id)))
    (response/redirect "/login")))


(defn handle-auth-login [req]
  (let [email       (get-in req [:params :email])
        password    (get-in req [:params :password])
        ;; user-id     (auth.model/auth-user db-url {:email email :password password})
        ]
    ;; (timbre/info (str "user authenticated successfully: " (util/uuid->str user-id)))
    (response/redirect "/lists")))
