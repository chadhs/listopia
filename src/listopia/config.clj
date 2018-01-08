(ns listopia.config
  (:require [environ.core                               :as environ]
            [taoensso.timbre                            :as timbre]
            [taoensso.timbre.appenders.3rd-party.sentry :as sentry]))


;; database config
(def db-url
  (environ/env :database-url))


;; logging config
(def reported-log-level
  (keyword (environ/env :reported-log-level)))


(def log-appender
  (environ/env :log-appender))


(defn configure-logging []
  (timbre/merge-config!
   {:appenders
    (cond (= "println" log-appender)
          {:println {:output-fn :inherit}}
          (= "sentry" log-appender)
          {:sentry-appender (merge
                             (sentry/sentry-appender (environ/env :sentry-dsn))
                             {:min-level reported-log-level})})}))
