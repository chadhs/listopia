(ns listopia.list.view.list
  (:require [listopia.home.view.layout :as home.view.layout])
  (:require [hiccup.core            :refer [html]]
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
        :placeholder "Name"
        :autofocus true}]]]
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


(defn delete-item-form
  [{:keys [item-id list-id]}]
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


(defn update-item-form
  [{:keys [item-id checked list-id]}]
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


(defn list-page
  [{:keys [list list-id list-items]}]
  (home.view.layout/page-layout
   (html
    [:a {:href "/lists"} "<< back to lists"]
    " | "
    [:a {:href "/logout"} "logout"]
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
            [:td (delete-item-form {:item-id (:id item)
                                    :list-id list-id})]
            [:td (update-item-form {:item-id (:id item)
                                    :checked (:checked item)
                                    :list-id list-id})]
            [:td (escape-html (:name item))]
            [:td (escape-html (:description item))]])]]
       [:div.col-sm-offset-1 "There are no items in this list."])]
    [:div.col-sm-8
     [:h2 "Create a new item"]
     (new-item list-id)])))
