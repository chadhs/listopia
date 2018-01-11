(ns listopia.home.view.login
  (:require [hiccup.core            :refer [html]]
            [hiccup.page            :refer [html5]]
            [hiccup.util            :refer [escape-html]]))


(defn login-page []
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
     [:p "login"]]
    [:script {:src "/assets/jquery/jquery.min.js"}]
    [:script {:src "/assets/bootstrap/js/bootstrap.min.js"}]]))
