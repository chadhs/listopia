(ns listopia.item.datomic
  (:require [datomic.api :as d]))

; setup an in memory db path
(def uri "datomic:mem://listopia")

; create the memory db
(d/create-database uri)
; define shorthand for connecting
(def conn (d/connect uri))
(def db (d/db conn))

; create schema about list items
(def list-item-schema [{:db/id (d/tempid :db.part/db)
                        :db/ident :listItem/uuid
                        :db/valueType :db.type/string
                        :db/unique :db.unique/identity
                        :db/cardinality :db.cardinality/one
                        :db/index true
                        :db/doc "unique item uuid"
                        :db.install/_attribute :db.part/db}
                       {:db/id (d/tempid :db.part/db)
                        :db/ident :listItem/name
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "name of list item"
                        :db.install/_attribute :db.part/db}
                       {:db/id (d/tempid :db.part/db)
                        :db/ident :listItem/detail
                        :db/valueType :db.type/string
                        :db/cardinality :db.cardinality/one
                        :db/doc "description of the list item"
                        :db.install/_attribute :db.part/db}
                       {:db/id (d/tempid :db.part/db)
                        :db/ident :listItem/complete
                        :db/valueType :db.type/boolean
                        :db/cardinality :db.cardinality/one
                        :db/doc "true if list item is complete"
                        :db.install/_attribute :db.part/db}])


; apply schema about list items
@(d/transact conn list-item-schema)

;;; crud now
;; create
(d/transact conn [{:db/id (d/tempid :db.part/db)
                   :listItem/uuid (str (d/squuid))
                   :listItem/name "do stuff"
                   :listItem/detail "things about the stuff to do"
                   :listItem/complete false}])
(d/transact conn [{:db/id (d/tempid :db.part/db)
                   :listItem/uuid (str (d/squuid))
                   :listItem/name "do things"
                   :listItem/detail "things need doing"
                   :listItem/complete false}])
(d/transact conn [{:db/id (d/tempid :db.part/db)
                   :listItem/uuid (str (d/squuid))
                   :listItem/name "make a thing"
                   :listItem/detail "make something"
                   :listItem/complete false}])
;; read
(d/q '[:find ?name ?detail ?complete
       :where
       [?uuid :listItem/name ?name]
       [?uuid :listItem/detail ?detail]
       [?uuid :listItem/complete ?complete]]
     db)

;; update
;; delete
;;; crud now

;;;;;;DOD CRUD;;;;;;;

;; create
(def db-after-create
  (-> (d/transact
       conn
       [[:db/add (d/tempid :db.part/user) :list/name "Hello world"]])
      deref
      :db-after))

;; read
(d/pull db-after-create '[*] [:list/name "Hello world"])

;; update
(-> (d/transact
     conn
     [[:db/add [:list/name "Hello world"]
       :db/doc "An entity with only demonstration value"]])
    deref
    :db-after
    (d/pull '[*] [:list/name "Hello world"]))

;; delete
(def db-after-delete
  (-> (d/transact
       conn
       [[:db.fn/retractEntity [:list/name "Hello world"]]])
      deref
      :db-after))

;; no present value for deleted entity
(d/pull db-after-delete '[*] [:list/name "Hello world"])

;; but everything ever recorded is still there
(def history (d/history db-after-delete))
(require '[clojure.pprint :as pp])
(->> (d/q '[:find ?e ?a ?v ?tx ?op
            :in $
            :where [?e :list/name "Hello world"]
                   [?e ?a ?v ?tx ?op]]
          history)
     (map #(zipmap [:e :a :v :tx :op] %))
     (sort-by :tx)
     (pp/print-table [:e :a :v :tx :op]))

;;;;;;DOD CRUD;;;;;;;





; create some sample list item data
(d/transact conn [{:db/id (d/tempid :db.part/user)
                   :list/name "todo item 1"
                   :list/description "don't forget to do item 1"
                   :list/checked false}
                  {:db/id (d/tempid :db.part/user)
                   :list/name "done item 1"
                   :list/description "boom this one is done!"
                   :list/checked true}])

;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;
;; point in time db value
(def dbpit (d/db conn))

;; query input and result are data
(def q-result (d/q '[:find ?e .
                     :where [?e :list/name "todo item 1"]]
                   dbpit))

(def q-false (d/q '[:find ?e .
                    :where [?e :list/checked false]]
                  dbpit))

(def q-true (d/q '[:find ?e .
                   :where [?e :list/checked true]]
                 dbpit))

;; entity is a navigable view over data
(def ent (d/entity dbpit q-result))

;; entities are lazy, so...
(d/touch ent)
(d/touch (d/entity dbpit q-false))
(d/touch (d/entity dbpit q-true))
;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;



; get all attributes about list item 1
;; this only works with unique values...
(d/pull (d/db conn) '[*] [:list/name "todo item 1"])

; find all checked items names and descriptions
(d/q '[:find ?item-name ?item-description
       :where [?list :list/checked true]
       [?list :list/name ?item-name]
       [?list :list/description ?item-description]]
     (d/db conn))

; find all unchecked items names and descriptions
(d/q '[:find ?item-name ?item-description
       :where [?list :list/checked false]
       [?list :list/name ?item-name]
       [?list :list/description ?item-description]]
     (d/db conn))

; look up item id

; retract an entity
(d/transact conn
            [[:db.fn/retractEntity [:list/name "todo item 1"]]])
;; (d/transact conn
;;             [[:db.fn/retractEntity eid]]) ; <- or by entity id

; retract a fact
(d/transact conn
            [[:db/retract [:list/name "todo item 1"] :list/description "don't forgot to do item 1 on tuesday"]])

; update a fact
;; WRONG, this is creating a new ID!!!
;; WRONG, this is creating a new ID!!!
;; WRONG, this is creating a new ID!!!
(d/transact conn [{:db/id (d/tempid :db.part/user)
                   :list/name "todo item 1"
                   :list/description "don't forget to do item 1 on tuesday"
                   :list/checked false}])

; add a new entity
(d/transact conn [{:db/id (d/tempid :db.part/user)
                   :list/name "todo item 2"
                   :list/description "item2 is important"
                   :list/checked false}])

; ask all facts about an entity
(d/q '[:find ?e ?a ?v ?tx ?op
       :in $
       :where [?e :list/name "todo item 1"]
       [?e ?a ?v ?tx ?op]]
     (d/history (d/db conn)))



;;;;
;;;; keep going
;;;;

;; ; look up Tiny's owner
;; (d/q '[:find ?owner-name
;;        :where [?dog :dog/name "Tiny"]
;;        [?owner :owner/dogs ?dog]
;;        [?owner :owner/name ?owner-name]]
;;      (d/db conn))

;; ; look up a dog's owner, passing the argument "Tiny" as the dog
;; (d/q '[:find ?owner-name
;;        :in $ ?dog-name
;;        :where [?dog :dog/name ?dog-name]
;;        [?owner :owner/dogs ?dog]
;;        [?owner :owner/name ?owner-name]]
;;      (d/db conn) "Tiny")

;; ; return dog name and breed of all dogs with a favorite treat of Cheese
;; (d/q '[:find [(pull ?dog [:dog/name :dog/breed]) ...]
;;        :where [?dog :dog/favorite-treat "Cheese"]]
;;      (d/db conn))

;; ; retract a fact about Tiny
;; (d/transact conn [[:db/retract [:dog/name "Tiny"] :dog/favorite-treat "Cheese"]])

;; ; db ref to use so queries are consistent (aka looking at the same point in time)
;; (def db-tiny-no-cheese (d/db conn))

;; (d/pull db-tiny-no-cheese '[*] [:dog/name "Tiny"])

;; ; ask all facts about Tiny ever recorded
;; (d/q '[:find ?e ?a ?v ?tx ?op
;;        :in $
;;        :where [?e :dog/name "Tiny"]
;;        [?e ?a ?v ?tx ?op]]
;;      (d/history db-tiny-no-cheese))

;; ; query at a point in time
;; (d/pull (d/as-of db-tiny-no-cheese 13194139534321) '[*] [:dog/name "Tiny"])

;; ; back to the future!
;; (d/pull db-tiny-no-cheese '[*] [:dog/name "Tiny"])

;; (d/pull db-tiny-no-cheese '[*] [:dog/name "Fido"])

;; (d/transact conn [{:db/id [:dog/name "Fido"]
;;                    :dog/favorite-treat "Eggs"}])

;; (d/pull (d/db conn) '[*] [:dog/name "Fido"])
