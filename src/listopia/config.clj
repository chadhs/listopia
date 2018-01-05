(ns listopia.config
  (:require [environ.core                               :as environ]
            [taoensso.timbre                            :as timbre]
            [taoensso.timbre.appenders.3rd-party.sentry :as sentry]))


;; database config
(def db-url
  (environ/env :database-url))


;; logging config
(defn configure-logging []
  (timbre/merge-config!
   {:appenders
    {:sentry-appender
     (sentry/sentry-appender (environ/env :sentry-dsn))}}))
