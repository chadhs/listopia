(ns listopia.home.view.login
  (:require [listopia.home.view.layout :as home.view.layout])
  (:require [hiccup.core               :refer [html]]
            [hiccup.util               :refer [escape-html]]
            [ring.util.anti-forgery    :as    anti-forgery]))


(defn login-form []
  (html
   [:form.form-horizontal
    {:method "POST" :action "/auth/login"}
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
  "populate an error message on page when error? is true"
  [& error]
  (when error (html [:div.alert.alert-danger {:role "alert"} "login error, please try again."])))


(defn login-page
  [& error]
  (home.view.layout/page-layout
   (html
    [:div.col-sm-8
     [:h2 "Login"]
     (when error (login-error? error))
     (login-form)])))
