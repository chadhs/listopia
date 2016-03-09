(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))

(defn home-handler[req]
  {:status 200
   :body "Hello, World!"
   :headers {}})

(defn goodbye-handler [req]
  {:status 200
   :body "Goodbye, cruel world!"
   :headers {}})

(defn about-handler [req]
  {:status 200
   :body "Hi, I'm Chad!\nI'm creating this site from scratch with Clojure and some cool libraries thanks to Eric Normand's awesome lispcast videos. ^_^"
   :headers {}})

(defn yo-handler [req]
  (let [name (get-in req [:route-params :name])]
    {:status 200
     :body (str "Yo! " name "!")
     :headers {}}))

(defn calc-handler [req]
  (let [x (Integer. (get-in req [:route-params :x]))
        y (Integer. (get-in req [:route-params :y]))
        op (get-in req [:route-params :op])
        op-fn (cond
                (= "+" op) +
                (= "-" op) -
                (= "*" op) *
                (= ":" op) /)]
    (if op-fn
      {:status 200
       :body (str (op-fn x y))
       :headers {}}
      {:status 404
       :body (str "Unknown operator: " op ". Supported operators are: + - * :")
       :headers {}})))

(defroutes app
  (GET "/" [] home-handler)
  (GET "/goodbye" [] goodbye-handler)
  (GET "/about" [] about-handler)
  (GET "/request" [] handle-dump)
  (GET "/yo/:name" [] yo-handler)
  (GET "/calc/:x/:op/:y" [] calc-handler)
  (not-found "Page not found."))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
