(ns listopia.list.view.list
  (:require [hiccup.core            :refer [html]]
            [hiccup.page            :refer [html5]]
            [hiccup.util            :refer [escape-html]]
            [ring.util.anti-forgery :as    anti-forgery]))


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
