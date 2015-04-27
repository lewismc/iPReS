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

(ns app.cache-test
  (:require [clojure.test :refer :all]
            [app.cache :refer :all]))

(deftest basic-cache-test
  (testing "adding a basic key and value"
    (not (= nil (cache-add :a 1))))
  (testing "cache contains value associated with basic key"
    (is (= true (cache-has? :a)))
    (is (= 1 (cache-lookup :a))))
  (testing "cache evicts basic key and value"
    (not (= nil (cache-drop-key :a)))
    (is (= nil (cache-lookup :a))))
  (testing "multiple simple things"
    (not (= nil (cache-add :a 1)))
    (not (= nil (cache-add :b 2)))
    (not (= nil (cache-add :c 3)))
    (not (= nil (cache-add :cowboy "yeehaw")))
    (is (= true (cache-has? :a)))
    (is (= 1 (cache-lookup :a)))
    (is (= true (cache-has? :b)))
    (is (= 2 (cache-lookup :b)))
    (is (= true (cache-has? :c)))
    (is (= 3 (cache-lookup :c)))
    (is (= true (cache-has? :cowboy)))
    (is (= "yeehaw" (cache-lookup :cowboy)))))

;; clear it out for the hell of it
(clear)

(deftest realistic-ish-cache-test
  (testing "cache adding with Korean"
    (not (= nil (cache-add :dataset-metadata-kr "기본 위성 자료")))
    (not (= false (cache-has? :dataset-metadata-kr)))
    (is (= "기본 위성 자료" (cache-lookup :dataset-metadata-kr))))
  (testing "cache adding with spanish"
    (not (= nil (cache-add :granule-metadata-es "datos básicos satélite")))
    (not (= false (cache-has? :granule-metadata-es)))
    (is (= "datos básicos satélite" (cache-lookup :granule-metadata-es))))
  (testing "eviction works"
    (not (= nil (cache-drop-key :granule-metadata-es)))
    (not (= nil (cache-drop-key :dataset-metadata-kr)))
    (is (= nil (cache-lookup :granule-metadata-es)))
    (is (= nil (cache-lookup :dataset-metadata-kr)))))