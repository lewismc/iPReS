; Licensed to the Apache Software Foundation (ASF) under one or more
; contributor license agreements. See the NOTICE file distributed with
; this work for additional information regarding copyright ownership.
; The ASF licenses this file to You under the Apache License, Version 2.0
; (the "License"); you may not use this file except in compliance with
; the License. You may obtain a copy of the License at
;
; http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.

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
