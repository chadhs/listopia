(ns listopia.list.view
  (:require [hiccup.core            :refer [html]]
            [hiccup.page            :refer [html5]]
            [hiccup.util            :refer [escape-html]]
            [ring.util.anti-forgery :as    anti-forgery]))


(defn new-list []
  (html
   [:form.form-horizontal
    {:method "POST" :action "/lists"}
    (anti-forgery/anti-forgery-field)
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :name-input}
      "Name"]
     [:div.col-sm-10
      [:input#name-input.form-control
       {:name :name
        :placeholder "Name"}]]]
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
    {:method "POST" :action (str "/lists/delete/" list-id)}
    (anti-forgery/anti-forgery-field)
    [:div.btn-group
     [:input.btn.btn-danger.btn-xs
      {:type :submit
       :value "Delete"}]]]))


(defn lists-page [lists]
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
             [:td [:a {:href (str "/lists/" (:id list))} (escape-html (:name list))]]
             [:td (escape-html (:description list))]])]]
        [:div.col-sm-offset-1 "There are no lists."])]
     [:div.col-sm-8
      [:h2 "Create a new list"]
      (new-list)]]
    [:script {:src "/assets/jquery/jquery.min.js"}]
    [:script {:src "/assets/bootstrap/js/bootstrap.min.js"}]]))

(defn list-page [list]
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
     [:h2 (:name list)]
     [:h3 (:description list)]
     [:div.row
      [:table.table.table-striped
       [:thead
        [:tr
         [:th.col-sm-2]
         [:th.col-sm-2]
         [:th "Item"]
         [:th "Description"]]]
       [:tbody
        [:tr
         [:td "delete"]
         [:td "checked"]
         [:td "name"]
         [:td "description"]]]]]]
    [:script {:src "/assets/jquery/jquery.min.js"}]
    [:script {:src "/assets/bootstrap/js/bootstrap.min.js"}]]))
