;; Ring handler test.  Makes use of ring.mock,
;; a mocking library for ring calls.
(ns demo.test.handler
  ;; More namespace aliasing here.
  (:require [clojure.test :refer :all]
            [demo.handler :refer :all]
            [ring.mock.request :as mock]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

;; Test written to always fail.
;; Exists purely to demonstrate testing a single test.
(deftest foo-test-always-fails
  (testing "foo"
    (is (= 1 2))))
