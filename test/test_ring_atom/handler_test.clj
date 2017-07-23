(ns test-ring-atom.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [test-ring-atom.handler :refer :all]))

(deftest test-app
  (testing "adding number 1"
    (let [response (app (mock/request :post "/add/1"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Number 1 added"))))

  (testing "adding number 1 again"
    (let [response (app (mock/request :post "/add/1"))]
      (is (= (:status response) 400))
      (is (= (:body response) "{\"error\":{\"message\":\"Number 1 already added\"}}"))))

  (testing "Map has number 1"
    (let [response (app (mock/request :get "/has-number/1"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Map has number 1"))))

  (testing "Map has not number 2"
    (let [response (app (mock/request :get "/has-number/2"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Map has not number 2"))))      

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
