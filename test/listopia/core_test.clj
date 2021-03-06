(ns listopia.core-test
  (:require [clojure.test          :refer :all])
  (:require [listopia.core         :refer :all]
            [listopia.config       :refer [db-url]]
            [listopia.list.model   :as    list.model]
            [listopia.list.handler :as    list.handler]
            [listopia.item.model   :as    item.model]
            [listopia.item.handler :as    item.handler]
            [listopia.util.core    :as    util]
            [listopia.util.db      :as    util.db]))


;; ensure a clean db state for each test
(defn test-db-reset [f]
  (util.db/test-clear-db! db-url)
  (f)
  (util.db/test-clear-db! db-url))
(use-fixtures :each test-db-reset)


;; static test values
(def test-user-id
  (java.util.UUID/fromString
   "7894c5fc-1991-4a85-8a7b-61cea48b5054"))


;; create a list
(deftest test-create-list
  (testing "list is created"
    (is (let [request (util/http-request-mock
                       :uri "/list/create"
                       :request-method :post
                       :params {:name "foo" :description "bar"}
                       :session {:identity test-user-id})]
          (= (get (list.handler/handle-create-list! request) :status)
             302)))))


(deftest delete-list
  (testing "list is deleted"
    (is (let [test-list (util/generate-test-list db-url test-user-id)
              list-id-str (get test-list :list-id-str)
              list-id-uuid (get test-list :list-id-uuid)
              request (util/http-request-mock
                       :uri (str "/list/delete/" list-id-str)
                       :request-method :post
                       :route-params {:list-id list-id-str})
              deleted? (= 302 (get (list.handler/handle-delete-list! request) :status))
              exists? (nil?
                       (list.model/read-list
                        db-url
                        {:list-id list-id-uuid
                         :user-id test-user-id}))]
          (and deleted? exists?)))))


;; create a list, fetch id, create item with list id
(deftest create-item
  (testing "item is created"
    (is (let [test-list (util/generate-test-list db-url test-user-id)
              list-id-str (get test-list :list-id-str)
              request (util/http-request-mock
                       :uri "/item/create"
                       :request-method :post
                       :params {:name "foo" :description "bar" :list-id list-id-str})]
          (= (get (item.handler/handle-create-item! request) :status)
             302)))))


;; create a list, fetch id, create item with list id, fetch item id, set checked true
(deftest check-item
  (testing "item is marked complete"
    (is (let [test-item (util/generate-test-item db-url test-user-id)
              item-id-str (get test-item :item-id-str)
              item-id-uuid (get test-item :item-id-uuid)
              list-id-str (get test-item :list-id-str)
              request (util/http-request-mock
                       :uri (str "/item/update/" item-id-str)
                       :request-method :post
                       :params {:list-id list-id-str :checked "true"}
                       :route-params {:item-id item-id-str})
              updated? (= 302 (get (item.handler/handle-update-item! request) :status))
              checked? (get (item.model/read-item db-url {:item-id item-id-uuid}) :checked)]
          (and updated? checked?)))))


;; create a list, fetch id, create item with list id, fetch item id, set checked false
(deftest uncheck-item
  (testing "item is marked incomplete"
    (is (let [test-item (util/generate-test-item db-url test-user-id)
              item-id-str (get test-item :item-id-str)
              item-id-uuid (get test-item :item-id-uuid)
              list-id-str (get test-item :list-id-str)
              request (util/http-request-mock
                       :uri (str "/item/update/" item-id-str)
                       :request-method :post
                       :params {:list-id list-id-str :checked "false"}
                       :route-params {:item-id item-id-str})
              updated? (= 302 (get (item.handler/handle-update-item! request) :status))
              unchecked? (false? (get (item.model/read-item db-url {:item-id item-id-uuid}) :checked))]
          (and updated? unchecked?)))))


;; create a list, fetch id, create item with list id, fetch item id, delete item
(deftest delete-item
  (testing "item is deleted"
    (is (let [test-item (util/generate-test-item db-url test-user-id)
              item-id-str (get test-item :item-id-str)
              item-id-uuid (get test-item :item-id-uuid)
              list-id-str (get test-item :list-id-str)
              request (util/http-request-mock
                       :uri (str "/item/delete/" item-id-str)
                       :request-method :post
                       :params {:list-id list-id-str}
                       :route-params {:item-id item-id-str})
              deleted? (= 302 (get (item.handler/handle-delete-item! request) :status))
              exists? (nil? (item.model/read-item db-url {:item-id item-id-uuid}))]
          (and deleted? exists?)))))
