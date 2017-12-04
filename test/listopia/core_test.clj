(ns listopia.core-test
  (:require [clojure.test          :refer :all])
  (:require [listopia.core         :refer :all]
            [listopia.db           :refer [database-url]]
            [listopia.list.model   :as    list.model]
            [listopia.list.handler :as    list.handler]
            [listopia.item.model   :as    item.model]
            [listopia.item.handler :as    item.handler]))


;; helper functions
(defn cs-http-request-mock
  "creates a request defaulting to http"
  [& {:keys [scheme server-port uri request-method params route-params]
      :or {scheme :http server-port 80 request-method :get}}]
  (let [base-mock {:protocol "HTTP/1.1"
                   :scheme scheme
                   :server-port server-port
                   :server-name "localhost"
                   :remote-addr "localhost"
                   :headers {"host" "localhost"}
                   :uri uri
                   :request-method request-method}]
    (cond-> base-mock
      params (assoc :params params)
      route-params (assoc :route-params route-params))))


(defn cs-uuid->str
  "return the plain string value of a given uuid."
  [uuid]
  (str (uuid :id)))


(deftest test-create-list
  (testing "list creation" 
    (is (let [request (cs-http-request-mock
                       :uri "/list/create"
                       :request-method :post
                       :params {:name "foo" :description "bar"})]
          (= (get (list.handler/handle-create-list! request) :status)
             302)))))


;; create a list, fetch id, delete the list
(deftest delete-list
  (testing "list deletion"
    (is (let [db database-url
              list-id (cs-uuid->str
                       (list.model/create-list! db {:name "foo" :description "bar"}))
              request (cs-http-request-mock
                       :uri (str "/list/delete/" list-id)
                       :request-method :post
                       :route-params {:list-id list-id})]
          (= (get (list.handler/handle-delete-list! request) :status)
             302)))))


;; create a list, fetch id, create item with list id
(deftest create-item
  (testing "item creation"
    (is (let [db database-url
              list-id (cs-uuid->str 
                       (list.model/create-list! db {:name "foo" :description "bar"}))
              request (cs-http-request-mock
                       :uri "/item/create"
                       :request-method :post
                       :params {:name "foo" :description "bar" :list-id list-id})] 
          (= (get (item.handler/handle-create-item! request) :status)
             302)))))


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
