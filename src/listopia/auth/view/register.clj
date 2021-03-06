(ns listopia.auth.view.register
  (:require [listopia.home.view.layout :as home.view.layout])
  (:require [hiccup.core               :refer [html]]
            [hiccup.util               :refer [escape-html]]
            [ring.util.anti-forgery    :as    anti-forgery]))

(defn register-form []
  (html
   [:form.form-horizontal
    {:method "POST" :action "/register"}
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
      "Name"]
     [:div.col-sm-10
      [:input#display-name-input.form-control
       {:name :display-name
        :placeholder "what you'd like to be called"}]]]
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :password-input}
      "Password"]
     [:div.col-sm-5
      [:input#password-input.form-control
       {:name :password
        :placeholder "password"
        :aria-describedby "passwordhelp"
        :type "password"
        :pattern ".{8,32}"
        :required true}]
      [:p {:id "passwordhelp"
           :class "form-text text-muted"}
       "password length must be 8-32"]]
     [:div.col-sm-5
      [:input#password-confirm.form-control
       {:name :passconfirm
        :placeholder "confirm"
        :aria-describedby "passwordhelp"
        :type "password"
        :required true}]
      [:p {:id "passwordhelp"
           :class "form-text text-muted"}
       "re-enter your password to confirm"]]]
    [:div.form-group
     [:div.col-sm-offset-2.col-sm-10
      [:input.btn.btn-success
       {:type :submit
        :value "Register"}]]]]))


(defn register-error?
  "populate an error message on page."
  [error]
  (let [error     (first error)
        error-msg (cond (= error :valid-email)    "please enter a valid email address"
                        (= error :valid-password) "invalid password choice, please try again"
                        (= error :password-match) "passwords do not match, please try again"
                        (= error :registered)     "this email is already registered, please login"
                        :else                     "registration error, please try again")]
    (html [:div.alert.alert-danger {:role "alert"} error-msg])))


(defn register-page
  [& error]
  (home.view.layout/page-layout
   (html
    [:div.col-sm-8
     [:h2 "Register"]
     (when error (register-error? error))
     (register-form)])))
