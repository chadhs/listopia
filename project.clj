(defproject listopia "0.2.0-SNAPSHOT"
  :description "make awesome lists"
  :url "https://github.com/chadhs/listopia"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;; core
                 [ring "1.4.0"]
                 [compojure "1.5.0"]
                 ;; database
                 [org.clojure/java.jdbc "0.4.2"]
                 [org.postgresql/postgresql "9.4.1208"]
                 ;; ui
                 [hiccup "1.0.5"]
                 ;; middleware
                 [ring/ring-anti-forgery "1.0.0"]
                 ;; hosted assests
                 [org.webjars/bootstrap "3.3.6"]
                 [org.webjars/font-awesome "4.5.0"]
                 [org.webjars/jquery "2.2.1"]
                 [ring-webjars "0.1.1"]]

  :min-lein-version "2.0.0"

  :uberjar-name "listopia.jar"

  :main listopia.core

  :profiles {:dev
             {:main listopia.core/-dev-main}})
