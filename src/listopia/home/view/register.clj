(ns listopia.home.view.register
  (:require [listopia.home.view.layout :as home.view.layout])
  (:require [hiccup.core               :refer [html]]
            [hiccup.util               :refer [escape-html]]
            [ring.util.anti-forgery    :as    anti-forgery]))

(defn register-form []
  (html
   [:form.form-horizontal
    {:method "POST" :action "/auth/register"}
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
     [:label.control-label.col-sm-2 {:for :display-name-input}
      "Display Name"]
     [:div.col-sm-10
      [:input#display-name-input.form-control
       {:name :display-name
        :placeholder "your name"}]]]
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :password-input}
      "Password"]
     [:div.col-sm-5
      [:input#password-input.form-control
       {:name :password
        :placeholder "password"
        :type "password"
        :required true}]]
     [:div.col-sm-5
      [:input#password-confirm.form-control
       {:name :passconfirm
        :placeholder "confirm"
        :type "password"
        :required true}]]]
    [:div.form-group
     [:div.col-sm-offset-2.col-sm-10
      [:input.btn.btn-success
       {:type :submit
        :value "Register"}]]]]))


(defn register-error?
  "populate an error message on page when error? is true"
  [& error]
  (when error
    (let [error-arg (str (first (flatten error)))
          error-msg (cond (= error-arg "password") "passwords do not match"
                          :else                    "registration error")]
      (html [:div.alert.alert-danger {:role "alert"} error-msg ", please try again."]))))


(defn register-page
  [& error]
  (home.view.layout/page-layout
   (html
    [:div.col-sm-8
     [:h2 "Register"]
     (when error (register-error? error))
     (register-form)])))
