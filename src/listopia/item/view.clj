(ns listopia.item.view
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [html h]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

(defn new-item []
  (html
   [:form.form-horizontal
    {:method "POST" :action "/items"}
    (anti-forgery-field)
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
        :value "New item"}]]]]))

(defn delete-item-form [id]
  (html
   [:form
    {:method "POST" :action (str "/items/" id)}
    (anti-forgery-field)
    [:input {:type :hidden
             :name "_method"
             :value "DELETE"}]
    [:div.btn-group
     [:input.btn.btn-danger.btn-xs
      {:type :submit
       :value "Delete"}]]]))

(defn update-item-form [id checked]
  (html
   [:form
    {:method "POST" :action (str "/items/" id)}
    (anti-forgery-field)
    [:input {:type :hidden
             :name "_method"
             :value "PUT"}]
    [:input {:type :hidden
             :name "checked"
             :value (if checked "false" "true")}]
    [:div.btn-group
     (if checked
       [:button.btn.btn-success.btn-xs [:i {:class "fa fa-check-square-o"}]]
       [:button.btn.btn-primary.btn-xs [:i {:class "fa fa-square-o"}]])]]))

(defn items-page [items]
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
     [:h2 "My Items"]
     [:div.row
      (if (seq items)
        [:table.table.table-striped
         [:thead
          [:tr
           [:th.col-sm-2]
           [:th.col-sm-2]
           [:th "Name"]
           [:th "Description"]]]
         [:tbody
          (for [item items]
            [:tr
             [:td (delete-item-form (:id item))]
             [:td (update-item-form (:id item) (:checked item))]
             [:td (h (:name item))]
             [:td (h (:description item))]])]]
        [:div.col-sm-offset-1 "There are no items."])]
     [:div.col-sm-8
      [:h2 "Create a new item"]
      (new-item)]]
    [:script {:src "/assets/jquery/jquery.min.js"}]
    [:script {:src "/assets/bootstrap/js/bootstrap.min.js"}]]))
