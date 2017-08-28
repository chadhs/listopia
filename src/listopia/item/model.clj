(ns listopia.item.model
  (:require [clojure.java.jdbc :as db]))

(defn create-item! [db name description]
  (:id (first (db/query
               db
               ["insert into items (name, description)
                 values (?, ?)
                 returning id"
                name
                description]))))

(defn update-item! [db id checked]
  (= [1] (db/execute!
          db
          ["update items
            set checked = ?
            where id = ?"
           checked
           id])))

(defn delete-item! [db id]
  (= [1] (db/execute!
          db
          ["delete from items
            where id = ?"
           id])))

(defn read-items [db]
  (db/query
   db
   ["select id, name, description, checked, date_created
     from items
     order by date_created"]))
