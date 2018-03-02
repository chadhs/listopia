(ns listopia.home.view.login
  (:require [hiccup.core            :refer [html]]
            [hiccup.page            :refer [html5]]
            [hiccup.util            :refer [escape-html]]
            [ring.util.anti-forgery :as    anti-forgery]))


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
        :autofocus true}]]]
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :password-input}
      "Password"]
     [:div.col-sm-10
      [:input#password-input.form-control
       {:name :password
        :placeholder "password"
        :type "password"}]]]
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
  (html5
   {:lang :en}
   [:head
    [:title "listopia"]
    [:meta {:name :viewport
            :content "width=device-width, initial-scale=1.0"}]
    [:link {:href "/assets/bootstrap/css/bootstrap.min.css"
            :rel :stylesheet}]
    [:link {:href "/assets/font-awesome/css/font-awesome.min.css"
            :rel :stylesheet}]
    [:link {:href "/css/main.css"
            :rel :stylesheet}]]
   [:body
    [:div.container
     [:h1 "Listopia"]
     [:div.col-sm-8
      [:h2 "Login"]
      (when error (login-error? error))
      (login-form)]]
    [:script {:src "/assets/jquery/jquery.min.js"}]
    [:script {:src "/assets/bootstrap/js/bootstrap.min.js"}]]))
