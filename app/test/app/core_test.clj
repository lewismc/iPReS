(ns app.core-test
  (:require [clojure.test :refer :all]
            [app.core :refer :all]
            [clojurewerkz.spyglass.client :as c]))

(def mem-conn (c/text-connection "localhost:2000"))

(deftest bananas
  (testing "basic cache"
    (is (= "bananas" (put-stuff-in-cache mem-conn "bananas")))))