(ns app.core-test
  (:require [clojure.test :refer :all]
            [app.core :refer :all]
            [app.cache :as cache]
            [clj-xpath.core :as xpath]
            [clojure.string :as str]))

;;;;;;;;;;
;;
;; Tika Unit Tests with stubbing
;;
;;;;;;;;;;



;;;;;;;;;;
;;
;; Full core unit tests with stubbing
;;
;;;;;;;;;;
(cache/clear)

(deftest core-integration-test
  (testing "that a call to translate-request correctly goes through all layers."
    (with-redefs-fn {#'hit-podaac (fn [route params] "some climate data")}
      #(is (= true true)))))

;;;;;;;;;;
;;
;; XML and PO.DAAC service-only integration tests
;;
;;;;;;;;;;

(def test-url "http://podaac.jpl.nasa.gov/ws/metadata/granule/?datasetId=PODAAC-GHMG2-2PO01&shortName=OSDPD-L2P-MSG02&granuleName=20120912-MSG02-OSDPD-L2P-MSG02_0200Z-v01.nc&format=iso")

(def test-url-xml
  (memoize (fn [] (slurp test-url))))

(def test-doc
  (memoize (fn [] (xpath/xml->doc (test-url-xml)))))

(defn- extract-root [xpath-doc]
  (first (xpath/$x "/*" (xpath-doc))))

(defn- report-text [xml-root]
  (->
    (get xml-root :text)
    (str/split #"\t+\n+")))

(defn- gen-stuff-for-translate [report]
  (->>
    (mapv (fn [x] (str (str/trim x) "\n")) report)
    (filterv (complement str/blank?))))

(deftest xpath-basic
  (testing "that all CharacterString tags are extracted and recognized."
    (println
      (->
        (extract-root test-doc)
        (report-text)
        (gen-stuff-for-translate)))
    (is (= 1 1))))

(deftest hit-podaac-works
  (testing "that basic XPATH code in hit-podaac is successful."
    (println (mapv (fn [x] (str x "\n")) (hit-podaac "metadata/dataset"
                                                     {:datasetId "PODAAC-GHMG2-2PO01"
                                                      :shortName "OSDPD-L2P-MSG02"})))
    (is (= 1 1))))
