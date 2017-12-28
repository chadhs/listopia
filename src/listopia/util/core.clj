(ns listopia.util.core
  (:require [listopia.list.model :as list.model]
            [listopia.item.model :as item.model]))


(defn http-request-mock
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


(defn uuid->str
  "return the plain string value of a given uuid."
  [uuid]
  (str (uuid :id)))


(defn hugsqluuid->javauuid
  "return the java.util.UUID/fromString uuid format from a hugsql uuid map format."
  [uuid]
  (uuid :id))


(defn generate-test-list
  "generates a test list returning a map of list-id, list-id-str, and list-id-uuid"
  [db]
  (let [list-id (list.model/create-list! db {:name "foo" :description "bar"})
        list-id-str (uuid->str list-id)
        list-id-uuid (hugsqluuid->javauuid list-id)]
    {:list-id list-id 
     :list-id-str list-id-str 
     :list-id-uuid list-id-uuid}))


(defn generate-test-item
  "generates a test item returning a map of item-id, item-id-str, and item-id-uuid"
  [db]
  (let [test-list (generate-test-list db)
        list-id-str (get test-list :list-id-str)
        list-id-uuid (get test-list :list-id-uuid)
        item-id (item.model/create-item! db {:name "foo" :description "bar" :list-id list-id-uuid})
        item-id-str (uuid->str item-id)
        item-id-uuid (hugsqluuid->javauuid item-id)]
    {:item-id item-id 
     :item-id-str item-id-str 
     :item-id-uuid item-id-uuid 
     :list-id-str list-id-str 
     :list-id-uuid list-id-uuid}))
