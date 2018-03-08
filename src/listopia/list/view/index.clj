(ns listopia.list.view.index
  (:require [listopia.home.view.layout :as home.view.layout])
  (:require [hiccup.core            :refer [html]]
            [hiccup.util            :refer [escape-html]]
            [ring.util.anti-forgery :as    anti-forgery]))


(defn new-list []
  (html
   [:form.form-horizontal
    {:method "POST" :action "/list/create"}
    (anti-forgery/anti-forgery-field)
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :name-input}
      "Name"]
     [:div.col-sm-10
      [:input#name-input.form-control
       {:name :name
        :placeholder "Name"
        :autofocus true}]]]
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :desc-input}
      "Description"]
     [:div.col-sm-10
      [:input#desc-input.form-control
       {:name :description
        :placeholder "Description"}]]]
    [:div.form-group
     [:div.col-sm-offset-2.col-sm-10
      [:input.btn.btn-primary
       {:type :submit
        :value "New list"}]]]]))


(defn delete-list-form [list-id]
  (html
   [:form
    {:method "POST" :action (str "/list/delete/" list-id)}
    (anti-forgery/anti-forgery-field)
    [:div.btn-group
     [:input.btn.btn-danger.btn-xs
      {:type :submit
       :value "Delete"}]]]))


(defn lists-page
  [lists]
  (home.view.layout/page-layout
   (html
    [:h2 "My Lists"]
    [:div.row
     (if (seq lists)
       [:table.table.table-striped
        [:thead
         [:tr
          [:th.col-sm-2]
          [:th "Name"]
          [:th "Description"]]]
        [:tbody
         (for [list lists]
           [:tr
            [:td (delete-list-form (:id list))]
            [:td [:a {:href (str "/list/" (:id list))} (escape-html (:name list))]]
            [:td (escape-html (:description list))]])]]
       [:div.col-sm-offset-1 "There are no lists."])]
    [:div.col-sm-8
     [:h2 "Create a new list"]
     (new-list)])))
