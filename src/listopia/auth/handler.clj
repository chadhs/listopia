(ns listopia.auth.handler
  (:require [listopia.config             :refer [db-url]]
            [listopia.auth.model         :as    auth.model]
            [listopia.util.core          :as    util]
            [listopia.auth.view.register :as    auth.view.register]
            [listopia.auth.view.login    :as    auth.view.login])
  (:require [ring.util.response          :as    response]
            [taoensso.timbre             :as    timbre]
            [buddy.hashers               :as    hashers]))


(defn handle-auth-register! [req]
  (let [email          (get-in req [:params :email])
        password       (get-in req [:params :password])
        passhash       (hashers/derive password {:alg :bcrypt+sha512})
        display-name   (get-in req [:params :display-name])
        params         (get req :params)
        register-error (get (util/valid-registration? {:params params :db db-url}) :error)
        account-id     (when (nil? register-error)
                         (auth.model/create-account!
                          db-url {:email email
                                  :password-hash passhash
                                  :display-name display-name}))]
    (if (not-empty account-id)
      (do
        (timbre/info (str "account created: " (util/uuid->str account-id) " : " email))
        (response/redirect "/login"))
      (do
        (timbre/error (str "account creation failure reason " register-error " " email))
        {:status 200
         :headers {"Content-Type" "text/html"}
         :body (auth.view.register/register-page register-error)}))))


(defn handle-auth-login [req]
  (let [email       (get-in req [:params :email])
        password    (get-in req [:params :password])
        authfacts   (auth.model/read-account-auth-facts db-url {:account-email email})
        account-id  (get authfacts :id)
        passhash    (get authfacts :password_hash)
        hashcalc    (hashers/check password passhash)
        login-error (cond (nil? account-id) :account-id
                          (false? hashcalc) :password)]
    (if (nil? login-error)
      (do
        (timbre/info (str "account authenticated successfully: " account-id " : " email))
        (let [session     (get req :session)
              new-session (assoc session :identity account-id)]
          (-> (response/redirect "/lists")
              (assoc :session new-session))))
      (do
        (timbre/error (str "account authentication failure reason " login-error " " account-id " : " email))
        {:status 200
         :headers {"Content-Type" "text/html"}
         :body (auth.view.login/login-page login-error)}))))


(defn handle-auth-login-page [req]
  (auth.view.login/login-page))


(defn handle-auth-register-page [req]
  (auth.view.register/register-page))
