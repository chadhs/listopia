(ns listopia.auth.handler
  (:require [listopia.config          :refer [db-url]]
            ;; [listopia.auth.model      :as    auth.model]
            [listopia.util.core       :as    util])
  (:require [ring.util.response       :as    response]
            [taoensso.timbre          :as    timbre]))


(defn handle-auth-register! [req]
  (let [email       (get-in req [:params :email])
        password    (get-in req [:params :password])
        ;; account-id     (auth.model/create-account! db-url {:email email :password password})
        ]
    ;; (timbre/info (str "account created: " (util/uuid->str account-id)))
    (response/redirect "/login")))


(defn handle-auth-login [req]
  (let [email       (get-in req [:params :email])
        password    (get-in req [:params :password])
        ;; account-id     (auth.model/auth-account db-url {:email email :password password})
        ]
    ;; (timbre/info (str "account authenticated successfully: " (util/uuid->str account-id)))
    (response/redirect "/lists")))
