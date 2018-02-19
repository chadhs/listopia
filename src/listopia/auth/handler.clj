(ns listopia.auth.handler
  (:require [listopia.config          :refer [db-url]]
            [listopia.auth.model      :as    auth.model]
            [listopia.util.core       :as    util])
  (:require [ring.util.response       :as    response]
            [taoensso.timbre          :as    timbre]))


(defn handle-auth-register! [req]
  (let [email        (get-in req [:params :email])
        password     (get-in req [:params :password])
        passconfirm  (get-in req [:params :passconfirm])
        display-name (get-in req [:params :display-name])
        account-id   (when (= password passconfirm)
                       (auth.model/create-account!
                        db-url {:email email
                                :password-hash password
                                :display-name display-name}))]
    (if account-id
      (do
        (timbre/info (str "account created: " (util/uuid->str account-id)))
        (response/redirect "/login"))
      (do
        (timbre/error (str "account creation failed for: " email))
        ;; this is broken
        ;; (response/not-found "passwords do not match try again")
        (response/redirect "/register")))))


(defn handle-auth-login [req]
  (let [email      (get-in req [:params :email])
        password   (get-in req [:params :password])
        account-id (auth.model/auth-account db-url {:account-email email})]
    (if (and
         (= email (get account-id :email))
         (= password (get account-id :password_hash)))
      (do
        (timbre/info (str "account authenticated successfully: " (util/uuid->str account-id)))
        (response/redirect "/lists"))
      (do
        (timbre/error (str "account authentication failed for: " (util/uuid->str account-id)))
        (response/redirect "/login")))))
