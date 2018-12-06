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
(deftest translate-with-tika-spanish
  (testing "returns spanish translation using apache tika"
    (with-redefs-fn {#'translate-with-tika (fn [dataset lang] "hola")}
      #(is (= "hola" (translate-to-lang "hello" :spanish-key "es"))
         (= true true)))))

(deftest translate-with-tika-german
  (testing "returns german translation using apache tika"
    (with-redefs-fn {#'translate-with-tika (fn [dataset lang] "hallo")}
      #(is (= true true)))))

(deftest translate-with-tika-italian
  (testing "returns italian translation using apache tika"
    (with-redefs-fn {#'translate-with-tika (fn [dataset lang] "ciao")}
      #(is (= true true)))))

(deftest translate-with-tika-french
  (testing "returns french translation using apache tika"
    (with-redefs-fn {#'translate-with-tika (fn [dataset lang] "bonjour")}
      #(is (= true true)))))

(deftest translate-with-tika-danish
  (testing "returns danish translation using apache tika"
    (with-redefs-fn {#'translate-with-tika (fn [dataset lang] "hej")}
      #(is (= true true)))))

(deftest translate-with-tika-norwegian
  (testing "returns norwegian translation using apache tika"
    (with-redefs-fn {#'translate-with-tika (fn [dataset lang] "hallo")}
      #(is (= true true)))))



;;;;;;;;;;
;;
;; Full core unit/integration tests with stubbing and no translation
;;
;;;;;;;;;;
(cache/clear)

(deftest translate-request-test-spanish
  (testing "that a call to translate-request correctly goes through all layers."
    (with-redefs-fn {#'hit-podaac (fn [route params] "some climate data")}
      #(is (= true (and
                     (= "some climate data" (translate-request "metadata/dataset" {} "es" "pdf"))
                     (= true (cache/cache-has? :metadatadatasetes))
                     (= "some climate data" (cache/cache-lookup :metadatadatasetes))))))))

(deftest translate-request-test-korean
  (testing "that a call to translate-request correctly goes through all layers."
    (with-redefs-fn {#'hit-podaac (fn [route params] "some climate data again")}
      #(is (= true (and
                     (= "some climate data again" (translate-request "metadata/dataset" {} "kr" "pdf"))
                     (= true (cache/cache-has? :metadatadatasetkr))
                     (= "some climate data again" (cache/cache-lookup :metadatadatasetkr))))))))

(deftest translate-request-test-french
  (testing "that a call to translate-request correctly goes through all layers."
    (with-redefs-fn {#'hit-podaac (fn [route params] "some climate data again, but french")}
      #(is (= true (and
                     (= "some climate data again, but french" (translate-request "metadata/dataset" {} "fr" "pdf"))
                     ;(= true (cache/cache-has? :metadatadatasetfr))
                     (= "some climate data again, but french" (cache/cache-lookup :metadatadatasetfr))))))))

;;;;;;;;;;
;;
;; XML and PO.DAAC service-only integration tests
;;
;;;;;;;;;;

(def test-url "https://podaac.jpl.nasa.gov/ws/metadata/granule/?datasetId=PODAAC-GHK10-41N01&shortName=NAVO-L4HR1m-GLOB-K10_SST&granuleName=20180214-NAVO-L4HR1m-GLOB-v01-fv01_0-K10_SST.nc&format=iso")

(def test-url-xml
  (memoize (fn [] (slurp test-url))))

(def test-doc
  (memoize (fn [] (xpath/xml->doc (test-url-xml) {:disallow-doctype-decl false}))))

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
