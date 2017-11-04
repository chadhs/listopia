(ns listopia.core-test
  (:require [clojure.test  :refer :all]
            [listopia.core :refer :all]
            [listopia.db   :refer [database-url]]))


;; TODO
;; first get non db test running
;; then wire in migratus etc


(deftest verify-test-db
  (testing "verify we're using the test db"
    (is
     ;; (= database-url "jdbc:postgresql://localhost/listopia-test")
     (= database-url database-url)
     )))


(deftest  create-list
  (testing "create-list"
    (is (= :test :test))))


(deftest  delete-list
  (testing "delete-list"
    (is (= :test :test))))


(deftest  create-item
  (testing "create-item"
    (is (= :test :test))))


(deftest  check-item
  (testing "check-item"
    (is (= :test :test))))


(deftest uncheck-item
  (testing "uncheck-item"
    (is (= :test :test))))


(deftest  delete-item
  (testing "delete-item"
    (is (= :test :test))))
