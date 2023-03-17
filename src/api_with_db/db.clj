(ns api-with-db.db 
  (:require [clojure.java.jdbc :as sql]))

(def connection-info { 
  :subprotocol "postgresql"
  :subname "//127.0.0.1:5432/people"
  :user ""
  :password "" })

;; (defn init []
;;   (sql/db-do-commands connection-info
;;                        (sql/create-table-ddl :people
;;                                               [[:id "varchar(256)" "primary key"]
;;                                                [:firstname "varchar(64)"]
;;                                                [:surname "varchar(64)"]]))
;;   (println "DB table created succesfully"))

(defn get-all []
  (sql/query connection-info ["SELECT * FROM people"]))

(defn get-person [id]
  (sql/query connection-info ["SELECT * FROM people where id = ?", id]))

(defn delete-person [id]
  (sql/execute! connection-info ["DELETE FROM people where id = ?", id]))

(defn create-person [body]
  (sql/insert! connection-info :people body))

(defn update-person [id body]
  (sql/update! connection-info :people body ["id  = ?" id]))

