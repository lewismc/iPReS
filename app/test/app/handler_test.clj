(ns app.handler-test
  (:require [clojure.test :refer :all]
            [app.handler :refer :all]))

(deftest is-supported?-happy
  (testing "happy case (english)"
    (is (= true (is-supported? "en")))))

(deftest is-supported?-sad
  (testing "sad case"
    (is (= false (is-supported? "a")))))

(deftest is-supported?-null
  (testing "null case"
    (is (= false (is-supported? nil)))))

(deftest is-supported?-empty
  (testing "empty case"
    (is (= false (is-supported? "")))))

(deftest is-supported?-stuff
  (testing "weiners"
    (is (= false is-supported? "weiners"))))