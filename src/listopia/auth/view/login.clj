(ns listopia.auth.view.login
  (:require [listopia.home.view.layout :as home.view.layout])
  (:require [hiccup.core               :refer [html]]
            [hiccup.util               :refer [escape-html]]
            [ring.util.anti-forgery    :as    anti-forgery]))


(defn login-form []
  (html
   [:form.form-horizontal
    {:method "POST" :action "/login"}
    (anti-forgery/anti-forgery-field)
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :email-input}
      "Email"]
     [:div.col-sm-10
      [:input#email-input.form-control
       {:name :email
        :placeholder "email"
        :autofocus true
        :required true}]]]
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :password-input}
      "Password"]
     [:div.col-sm-10
      [:input#password-input.form-control
       {:name :password
        :placeholder "password"
        :type "password"
        :required true}]]]
    [:div.form-group
     [:div.col-sm-offset-2.col-sm-10
      [:input.btn.btn-primary
       {:type :submit
        :value "Login"}]]]]))


(defn login-error?
  "populate an error message on page."
  [error]
  (let [error        (first error)
        error-prefix (cond (= error :account-id) "invalid email or password"
                           (= error :password)   "invalid email or password"
                           :else                 "invalid email or password")
        error-msg    (str error-prefix ", please try again.")]
    (html [:div.alert.alert-danger {:role "alert"} error-msg])))


(defn login-page
  [& error]
  (home.view.layout/page-layout
   (html
    [:div.col-sm-8
     [:h2 "Login"]
     (when error (login-error? error))
     (login-form)])))
