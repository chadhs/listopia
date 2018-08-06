(defproject listopia "0.7.0-SNAPSHOT"
  :description "make awesome lists"
  :url "https://github.com/chadhs/listopia"
  :license {:name "BSD 3-Clause License"
            :url "https://choosealicense.com/licenses/bsd-3-clause/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 ;;; core
                 [ring "1.6.3"]
                 [compojure "1.6.0"]
                 ;;; environment
                 [environ "1.1.0"]
                 ;;; database
                 [com.layerware/hugsql "0.4.9"]
                 [org.postgresql/postgresql "42.2.2"]
                 [migratus "1.0.6"]
                 ;;; logging
                 [com.taoensso/timbre "4.10.0"]
                 [raven-clj "1.5.2"] ; timbre sentry support
                 ;;; security
                 [buddy "2.0.0"]
                 ;;; ui
                 [hiccup "1.0.5"]
                 ;;; middleware
                 [ring/ring-defaults "0.3.2"]
                 ;;; hosted assests
                 [ring-webjars "0.2.0"]
                 [org.webjars/bootstrap "3.3.7-1"]
                 [org.webjars/font-awesome "4.7.0"]
                 [org.webjars/jquery "2.2.4"]]

  :plugins [[lein-environ "1.1.0"]
            [lein-ring "0.12.4"]
            [migratus-lein "0.5.7"]]

  :ring {:handler listopia.core/app}

  :migratus {:store :database
             :migration-dir "migrations"
             :db ~(get (System/getenv) "DATABASE_URL")}

  :min-lein-version "2.0.0"

  :uberjar-name "listopia.jar"

  :main listopia.core

  :profiles {:uberjar {:aot :all}
             :dev {:main listopia.core/-dev-main}
             :test {}})
