(ns listopia.list.view.list
  (:require [hiccup.core            :refer [html]]
            [hiccup.page            :refer [html5]]
            [hiccup.util            :refer [escape-html]]
            [ring.util.anti-forgery :as    anti-forgery]))


(defn new-item [list-id]
  (html
   [:form.form-horizontal
    {:method "POST" :action "/item/create"}
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
    [:input {:type :hidden
             :name "list-id"
             :value list-id}]
    [:div.form-group
     [:div.col-sm-offset-2.col-sm-10
      [:input.btn.btn-primary
       {:type :submit
        :value "New item"}]]]]))


(defn delete-item-form [item-id list-id]
  (html
   [:form
    {:method "POST" :action (str "/item/delete/" item-id)}
    (anti-forgery/anti-forgery-field)
    [:input {:type :hidden
             :name "list-id"
             :value list-id}]
    [:div.btn-group
     [:input.btn.btn-danger.btn-xs
      {:type :submit
       :value "Delete"}]]]))


(defn update-item-form [item-id checked list-id]
  (html
   [:form
    {:method "POST" :action (str "/item/update/" item-id)}
    (anti-forgery/anti-forgery-field)
    [:input {:type :hidden
             :name "checked"
             :value (if checked "false" "true")}]
    [:input {:type :hidden
             :name "list-id"
             :value list-id}]
    [:div.btn-transparent
     [:div.btn-group
      (if checked
        [:button.btn.btn-default.btn-xs [:i {:class "fa fa-check-square fa-2x"}]]
        [:button.btn.btn-default.btn-xs [:i {:class "fa fa-square-o fa-2x"}]])]]]))


(defn list-page [list list-id list-items]
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
     [:a {:href "/lists"} "<< back to lists"]
     [:h2 (:name list)]
     [:h3 (:description list)]
     [:div.row
      (if (seq list-items)
        [:table.table.table-striped
         [:thead
          [:tr
           [:th.col-sm-2]
           [:th.col-sm-2]
           [:th "Name"]
           [:th "Description"]]]
         [:tbody
          (for [item list-items]
            [:tr
             [:td (delete-item-form (:id item) list-id)]
             [:td (update-item-form (:id item) (:checked item) list-id)]
             [:td (escape-html (:name item))]
             [:td (escape-html (:description item))]])]]
        [:div.col-sm-offset-1 "There are no items in this list."])]
     [:div.col-sm-8
      [:h2 "Create a new item"]
      (new-item list-id)]]
    [:script {:src "/assets/jquery/jquery.min.js"}]
    [:script {:src "/assets/bootstrap/js/bootstrap.min.js"}]]))
