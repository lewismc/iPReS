(ns demo.test.core
  (:require [clojure.test :refer :all]
            [demo.core :refer :all]))

(deftest test-to-pig-latin
  (testing "happy cases"
    (is (= "orangeay" (to-pig-latin "orange")))
    (is (= "hillippay" (to-pig-latin "phillip")))))
