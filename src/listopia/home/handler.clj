(ns listopia.home.handler
  (:require [listopia.home.view.index :as home.view.index]))


(defn handle-home-index [req]
  (home.view.index/home-page))
