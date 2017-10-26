(defproject listopia "0.3.0-SNAPSHOT"
  :description "make awesome lists"
  :url "https://github.com/chadhs/listopia"
  :license {:name "BSD 3-Clause License"
            :url "https://choosealicense.com/licenses/bsd-3-clause/"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;;; core
                 [ring "1.6.2"]
                 [compojure "1.6.0"]
                 ;;; environment
                 [environ "1.1.0"]
                 ;;; database
                 [com.layerware/hugsql "0.4.7"]
                 [org.postgresql/postgresql "42.1.4"]
                 [migratus "1.0.0"]
                 ;;; ui
                 [hiccup "1.0.5"]
                 ;;; middleware
                 [ring/ring-anti-forgery "1.1.0"]
                 ;;; hosted assests
                 [ring-webjars "0.2.0"]
                 [org.webjars/bootstrap "3.3.7-1"]
                 [org.webjars/font-awesome "4.7.0"]
                 [org.webjars/jquery "2.2.4"]]

  :plugins [[lein-environ "1.1.0"]
            [lein-ring "0.12.1"]
            [migratus-lein "0.5.2"]]

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
