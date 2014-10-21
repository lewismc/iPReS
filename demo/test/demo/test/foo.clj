(ns demo.test.foo
  (:require [clojure.test :refer :all]))

;; Test written to always fail.
;; Exists purely to demonstrate testing a single test.
(deftest foo-test-always-fails
  (testing "foo"
    (is (= 1 2))))

;; Dummy test written to always pass.
(deftest foo-test-always-passes
  (testing "foo"
    (is (= 1 1))))
