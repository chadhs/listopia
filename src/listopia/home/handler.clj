(ns listopia.home.handler
  (:require [listopia.home.view.index    :as home.view.index]
            [listopia.home.view.login    :as home.view.login]
            [listopia.home.view.register :as home.view.register]))


(defn handle-home-index [req]
  (home.view.index/home-page))


(defn handle-home-login [req]
  (let [error? (:error (:route-params req))]
    (if error?
      (home.view.login/login-page error?)
      (home.view.login/login-page))))


(defn handle-home-register [req]
  (let [error? (:error (:route-params req))]
    (if error?
      (home.view.register/register-page error?)
      (home.view.register/register-page))))
