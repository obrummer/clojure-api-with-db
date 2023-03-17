(ns api-with-db.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [api-with-db.db :as db]
            [ring.util.response :refer [response]]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-people []
  (db/get-all))

(defn insert-new-person [body]
  (let [id (uuid)]
    (db/create-person (assoc body "id" id))
    (get-people)))

(defn get-person-by-id [id]
  (let [results (db/get-person id)]
    (if (empty? results)
      {:status 404}
      (response (first results)))))

(defn delete-person-by-id [id]
  (db/delete-person id)
  {:status 204})

(defn update-person-by-id [id body]
  (db/update-person id (assoc body "id" id))
  (get-person-by-id id))

(defroutes app-routes
  (context "/people" []
    (GET "/" [] (get-people))
    (POST "/" {body :body} (insert-new-person body))
    (context "/:id" [id]
       (GET "/" [] (get-person-by-id id))
       (PUT "/" {body :body} (update-person-by-id id body))
       (DELETE "/" [] (delete-person-by-id id))))
  (route/not-found "Not Found"))

(defn wrap-content-json [h]
  (fn [req] (assoc-in (h req) [:headers "Content-Type"] "application/json")))

(def app
  (wrap-defaults
   (-> (wrap-json-body app-routes)
       (wrap-json-response)
       (wrap-content-json))
   (assoc-in site-defaults [:security :anti-forgery] false)))
