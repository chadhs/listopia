(ns listopia.core-test
  (:require [clojure.test                 :refer :all])
  (:require [listopia.core                :refer :all]
            [listopia.list.model          :as    list.model]
            [listopia.list.handler        :as    list.handler]))


;; workaround
(def database-url "jdbc:postgresql://localhost/listopia-test")
(def db database-url)


;; TODO
;;; then wire in migratus setup ↓
;;; dropdb listopia-test && createdb listopia-test && lein with-profile test migratus migrate


;; helper functions
(defn cs-http-request-mock
  "creates a request defaulting to http"
  [& {:keys [scheme server-port uri request-method params]
      :or {scheme :http server-port 80 request-method :get}}]
  (let [base-mock {:protocol "HTTP/1.1"
                   :scheme scheme
                   :server-port server-port
                   :server-name "localhost"
                   :remote-addr "localhost"
                   :headers {"host" "localhost"}
                   :uri uri
                   :request-method request-method}]
    (if params
      (assoc base-mock :params params)
      base-mock)))


(deftest verify-test-db
  (testing "verify we're using the test db"
    (is
     (= database-url "jdbc:postgresql://localhost/listopia-test"))))


(deftest test-create-list
  (is (let [request (cs-http-request-mock 
                     :uri "/list/create" 
                     :request-method :post 
                     :params {:name "foo" :description "bar"})]
        (= (get (list.handler/handle-create-list! request) :status)
           302))))


;; create a list, fetch id, delete the list
(deftest delete-list
  (testing "delete-list"
    (is (= :test :test))))


;; create a list, fetch id, create item with list id
(deftest create-item
  (testing "create-item"
    (is (= :test :test))))


;; create a list, fetch id, create item with list id, fetch item id, set checked true
(deftest check-item
  (testing "check-item"
    (is (= :test :test))))


;; create a list, fetch id, create item with list id, fetch item id, set checked false
(deftest uncheck-item
  (testing "uncheck-item"
    (is (= :test :test))))


;; create a list, fetch id, create item with list id, fetch item id, delete item
(deftest delete-item
  (testing "delete-item"
    (is (= :test :test))))
