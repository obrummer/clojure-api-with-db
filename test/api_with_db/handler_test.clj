(ns api-with-db.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [api-with-db.handler :refer :all]))

(deftest test-app
  (testing "not-found route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 404)))))
