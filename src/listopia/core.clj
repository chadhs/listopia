(ns listopia.core
  (:require [listopia.item.model :as items]
            [listopia.item.handler :refer [handle-index-items
                                         handle-create-item
                                         handle-delete-item
                                         handle-update-item]])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))

(def db (or
         (System/getenv "DATABASE_URL")
         "jdbc:postgresql://localhost/listopia"))

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

(defroutes routes
  (GET "/" [] handle-index-items)
  (GET "/goodbye" [] goodbye-handler)
  (GET "/about" [] about-handler)
  (ANY "/request" [] handle-dump)
  (GET "/yo/:name" [] yo-handler)
  (GET "/calc/:x/:op/:y" [] calc-handler)
  ;; list app routes
  (GET "/items" [] handle-index-items)
  (POST "/items" [] handle-create-item)
  (DELETE "/items/:item-id" [] handle-delete-item)
  (PUT "/items/:item-id" [] handle-update-item)
  (not-found "Page not found."))

(defn wrap-db [hdlr]
  (fn [req]
    (hdlr (assoc req :listopia/db db))))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "server"] "nomad")))

(def sim-methods {"PUT" :put
                  "DELETE" :delete})

(defn wrap-simulated-methods [hdlr]
  (fn [req]
    (if-let [method (and (= :post (:request-method req))
                         (sim-methods (get-in req [:params "_method"])))]
      (hdlr (assoc req :request-method method))
      (hdlr req))))

(def app
  (-> routes
      wrap-simulated-methods
      wrap-params
      wrap-db
      wrap-server
      (wrap-resource "static")
      wrap-file-info
      wrap-webjars))

(defn -main
  ([] (-main 8000))
  ([port] (items/create-table! db)
          (jetty/run-jetty app
                           {:port (Integer. port)})))

(defn -dev-main
  ([] (-dev-main 8000))
  ([port] (items/create-table! db)
          (jetty/run-jetty (wrap-reload #'app)
                           {:port (Integer. port)})))
