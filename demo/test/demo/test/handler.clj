;; Ring handler test.  Makes use of ring.mock,
;; a mocking library for ring calls.
(ns demo.test.handler
  ;; More namespace aliasing here.
  (:require [clojure.test :refer :all]
            [demo.handler :refer :all]
            [ring.mock.request :as mock]))

(deftest test-app

  ;; Tests that the content returned is JSON.
  (testing "main route"
    (let [response (app (mock/request :get "/translator"))]
      (is (= (:status response) 200))
      (is (= (:get-in response [:headers "Content-Type"]) "application-json"))))

  ;; Tests that providing a bogus route returns a 404.
  (testing "not-found route"
    (let [response (app (mock/request :get "bogus-route"))]
      (is (= (:status response) 404)))))
