(ns listopia.core
  (:require [listopia.route                 :as    route]
            [listopia.middleware            :as    middleware]
            [listopia.config                :as    config])
  (:require [ring.adapter.jetty             :as    jetty]
            [ring.middleware.defaults       :refer :all]
            [ring.middleware.webjars        :refer [wrap-webjars]]
            [ring.middleware.reload         :refer [wrap-reload]]
            [ring.middleware.session.cookie :refer [cookie-store]])
  (:gen-class))


(config/configure-logging)


(def app
  (-> route/combined-routes
      ;; wrap-defaults includes ring middleware in the correct order to provide:
      ;; csrf protection, session data, url parameters, static assets, and more
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:session :store] (cookie-store {:key config/session-cookie-key}))
           (assoc-in [:session :cookie-attrs] {:max-age 3600})))
      wrap-webjars             ; set path for webjar assets
      middleware/wrap-server)) ; set server name header


(defn -main
  ([]     (-main 8000))
  ([port] (jetty/run-jetty app
                           {:port (Integer. port)})))


(defn -dev-main
  ([]     (-dev-main 8000))
  ([port] (jetty/run-jetty (wrap-reload #'app)
                           {:port (Integer. port)})))
