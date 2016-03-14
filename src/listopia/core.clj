(ns listopia.core
  (:require [listopia.item.model :as model :refer [dburl]]
            [listopia.item.middleware :refer [wrap-db
                                              wrap-server]]
            [listopia.item.route :refer [routes]])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            ;; [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.webjars :refer [wrap-webjars]]))

(def app
  (-> routes
      wrap-anti-forgery        ; csrf protection
      wrap-session             ; session data
      wrap-params              ; url-encoded param support
      wrap-db                  ; add db/conn string into params
      wrap-server              ; set server name header
      (wrap-resource "static") ; set static asset path
      wrap-file-info           ; add file info to static resources
      wrap-webjars))           ; set asset path for webjar assets

;; (def app
;;   (-> routes
;;       wrap-simulated-methods
;;       wrap-db
;;       wrap-webjars
;;       (wrap-defaults
;;        (-> site-defaults
;;            (assoc-in [:static :resources] "static")))))

(defn -main
  ([] (-main 8000))
  ([port] (model/create-table! dburl)
          (jetty/run-jetty app
                           {:port (Integer. port)})))

(defn -dev-main
  ([] (-dev-main 8000))
  ([port] (model/create-table! dburl)
          (jetty/run-jetty (wrap-reload #'app)
                           {:port (Integer. port)})))
