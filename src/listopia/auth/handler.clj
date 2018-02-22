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
        hashcalc   password
        authfacts  (auth.model/read-account-auth-facts db-url {:account-email email})
        account-id (get authfacts :id)
        email      (get authfacts :email)
        passhash   (get authfacts :password_hash)]
    (cond (= hashcalc passhash)
          (do
            (timbre/info (str "account authenticated successfully: " account-id " : " email))
            (response/redirect "/lists"))
          (nil? account-id)
          (do
            (timbre/error (str "account authentication failed: invalid account-id"))
            (response/redirect "/login"))
          :else
          (do
            (timbre/error (str "account authentication failed for: " account-id " : " email))
            (response/redirect "/login")))))
