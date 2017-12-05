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


(defn cs-hugsqluuid->javauuid 
  "return the java.util.UUID/fromString uuid format from a hugsql uuid map format."
  [uuid]
  (uuid :id))


;; tests
(deftest test-create-list
  (testing "list is created" 
    (is (let [request (cs-http-request-mock
                       :uri "/list/create"
                       :request-method :post
                       :params {:name "foo" :description "bar"})]
          (= (get (list.handler/handle-create-list! request) :status)
             302)))))


;; create a list, fetch id, delete the list
(deftest delete-list
  (testing "list is deleted"
    (is (let [db database-url
              list-id (list.model/create-list! db {:name "foo" :description "bar"})
              list-id-str (cs-uuid->str list-id)
              list-id-uuid (cs-hugsqluuid->javauuid list-id)
              request (cs-http-request-mock
                       :uri (str "/list/delete/" list-id-str)
                       :request-method :post
                       :route-params {:list-id list-id-str})
              deleted? (= 302 (get (list.handler/handle-delete-list! request) :status))
              exists? (nil? (list.model/read-list db {:list-id list-id-uuid}))] 
          (and deleted? exists?)))))


;; create a list, fetch id, create item with list id
(deftest create-item
  (testing "item is created"
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
  (testing "item is marked complete"
    (is (let [db database-url
              list-id (list.model/create-list! db {:name "foo" :description "bar"})
              list-id-str (cs-uuid->str list-id)
              list-id-uuid (cs-hugsqluuid->javauuid list-id)
              item-id (item.model/create-item! db {:name "foo" :description "bar" :list-id list-id-uuid})
              item-id-str (cs-uuid->str item-id)
              item-id-uuid (cs-hugsqluuid->javauuid item-id)
              request (cs-http-request-mock
                       :uri (str "/item/update/" item-id-str)
                       :request-method :post
                       :params {:list-id list-id-str :checked "true"}
                       :route-params {:item-id item-id-str})
              updated? (= 302 (get (item.handler/handle-update-item! request) :status))
              checked? (get (item.model/read-item db {:item-id item-id-uuid}) :checked)] 
          (and updated? checked?)))))


;; create a list, fetch id, create item with list id, fetch item id, set checked false
(deftest uncheck-item
  (testing "item is marked incomplete"
    (is (let [db database-url
              list-id (list.model/create-list! db {:name "foo" :description "bar"})
              list-id-str (cs-uuid->str list-id)
              list-id-uuid (cs-hugsqluuid->javauuid list-id)
              item-id (item.model/create-item! db {:name "foo" :description "bar" :list-id list-id-uuid})
              item-id-str (cs-uuid->str item-id)
              item-id-uuid (cs-hugsqluuid->javauuid item-id)
              request (cs-http-request-mock
                       :uri (str "/item/update/" item-id-str)
                       :request-method :post
                       :params {:list-id list-id-str :checked "false"}
                       :route-params {:item-id item-id-str})
              updated? (= 302 (get (item.handler/handle-update-item! request) :status))
              unchecked? (false? (get (item.model/read-item db {:item-id item-id-uuid}) :checked))] 
          (and updated? unchecked?)))))


;; create a list, fetch id, create item with list id, fetch item id, delete item
(deftest delete-item
  (testing "item is deleted"
    (is (let [db database-url
              list-id (list.model/create-list! db {:name "foo" :description "bar"})
              list-id-str (cs-uuid->str list-id)
              list-id-uuid (cs-hugsqluuid->javauuid list-id)
              item-id (item.model/create-item! db {:name "foo" :description "bar" :list-id list-id-uuid})
              item-id-str (cs-uuid->str item-id)
              item-id-uuid (cs-hugsqluuid->javauuid item-id)
              request (cs-http-request-mock
                       :uri (str "/item/delete/" item-id-str)
                       :request-method :post
                       :params {:list-id list-id-str}
                       :route-params {:item-id item-id-str})
              deleted? (= 302 (get (item.handler/handle-delete-item! request) :status))
              exists? (nil? (item.model/read-item db {:item-id item-id-uuid}))]
          (and deleted? exists?)))))
