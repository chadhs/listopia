(ns listopia.home.view.index
  (:require [listopia.home.view.layout :as home.view.layout])
  (:require [hiccup.core               :refer [html]]))


(defn home-page []
  (home.view.layout/page-layout
   (html
    [:p "Welcome; login or sign up for great glory!"]
    [:p "More cool marketing information about how great Listopia is..."]
    [:a.btn.btn-primary {:href "/login"} "login"]
    [:a.btn.btn-success {:href "/register"} "register"])))
