(ns app.cache-test
  (:require [clojure.test :refer :all]
            [app.cache :refer :all]))

(deftest basic-cache-test
  (testing "adding a basic key and value"
    (not (= nil (add :a 1))))
  (testing "cache contains value associated with basic key"
    (is (= true (cache-has? :a)))
    (is (= 1 (lookup :a))))
  (testing "cache evicts basic key and value"
    (not (= nil (drop-key :a)))
    (is (= nil (lookup :a))))
  (testing "multiple simple things"
    (not (= nil (add :a 1)))
    (not (= nil (add :b 2)))
    (not (= nil (add :c 3)))
    (not (= nil (add :cowboy "yeehaw")))
    (is (= true (cache-has? :a)))
    (is (= 1 (lookup :a)))
    (is (= true (cache-has? :b)))
    (is (= 2 (lookup :b)))
    (is (= true (cache-has? :c)))
    (is (= 3 (lookup :c)))
    (is (= true (cache-has? :cowboy)))
    (is (= "yeehaw" (lookup :cowboy)))))

;; clear it out for the hell of it
(clear)

(deftest realistic-ish-cache-test
  (testing "cache adding with Korean"
    (not (= nil (add :dataset-metadata-kr "기본 위성 자료")))
    (not (= false (cache-has? :dataset-metadata-kr)))
    (is (= "기본 위성 자료" (lookup :dataset-metadata-kr))))
  (testing "cache adding with spanish"
    (not (= nil (add :granule-metadata-es "datos básicos satélite")))
    (not (= false (cache-has? :granule-metadata-es)))
    (is (= "datos básicos satélite" (lookup :granule-metadata-es))))
  (testing "eviction works"
    (not (= nil (drop-key :granule-metadata-es)))
    (not (= nil (drop-key :dataset-metadata-kr)))
    (is (= nil (lookup :granule-metadata-es)))
    (is (= nil (lookup :dataset-metadata-kr)))))