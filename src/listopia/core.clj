(ns listopia.core
  (:require [listopia.item.middleware     :as    item.middleware]
            [listopia.route               :as    route])
  (:require [ring.adapter.jetty           :as    jetty]
            [ring.middleware.reload       :refer [wrap-reload]]
            [ring.middleware.params       :refer [wrap-params]]
            [ring.middleware.resource     :refer [wrap-resource]]
            [ring.middleware.file-info    :refer [wrap-file-info]]
            [ring.middleware.session      :refer [wrap-session]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.webjars      :refer [wrap-webjars]])
  (:gen-class))


(def app
  (-> route/combined-routes
      wrap-anti-forgery           ; csrf protection
      wrap-session                ; session data
      wrap-params                 ; url-encoded param support
      item.middleware/wrap-server ; set server name header
      (wrap-resource "static")    ; set static asset path
      wrap-file-info              ; add file info to static resources
      wrap-webjars))              ; set asset path for webjar assets


(defn -main
  ([]     (-main 8000))
  ([port] (jetty/run-jetty app
                           {:port (Integer. port)})))


(defn -dev-main
  ([]     (-dev-main 8000))
  ([port] (jetty/run-jetty (wrap-reload #'app)
                           {:port (Integer. port)})))
