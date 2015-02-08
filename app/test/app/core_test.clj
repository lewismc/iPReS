(ns app.core-test
  (:require [clojure.test :refer :all]
            [app.core :refer :all]
            [app.cache :as cache]
            [clj-xpath.core :as xpath]
            [clojure.string :as str]))

(deftest translate-request-test
  (testing "basic coverage of translate-request with empty cache"
    (with-redefs-fn {#'hit-podaac (fn [route params] "<?xml version='1.0' encoding='UTF-8'?><foo><bar><baz>The baz value</baz></bar></foo>")}
                    #(is (= "<?xml version='1.0' encoding='UTF-8'?><foo><bar><baz>The baz value</baz></bar></foo>"
                            (translate-request "metadata/dataset"
                                               {:datasetId "PODAAC-GHMG2-2PO01"
                                                :shortName "OSDPD-L2P-MSG02"}
                                               "kr"
                                               "xml"))))
    (is (= true (cache/cache-has? (keyword "metadatadatasetkr"))))
    (is (= "<?xml version='1.0' encoding='UTF-8'?><foo><bar><baz>The baz value</baz></bar></foo>"
           (cache/cache-lookup (keyword "metadatadatasetkr")))))

  (testing "basic coverage of translate-request with cache that has a key"
    (is (= (cache/cache-lookup (keyword "metadatadatasetkr")) (translate-request "metadata/dataset"
                                                                                 {:datasetId "PODAAC-GHMG2-2PO01"
                                                                                  :shortName "OSDPD-L2P-MSG02"}
                                                                                 "kr"
                                                                                 "xml")))))

(deftest convert-to-format-test
  (testing "basic test"
    (is (= "<?xml version='1.0' encoding='UTF-8'?><foo><bar><baz>The baz value</baz></bar></foo>"
           (convert-to-format "<foo><bar><baz>Te baz value</baz></bar></foo>" "xml")))))

;;
;; XML test stuff
;;

(def test-url "http://podaac.jpl.nasa.gov/ws/metadata/dataset/?datasetId=PODAAC-GHMG2-2PO01&shortName=OSDPD-L2P-MSG02")

(def test-url-xml
  (memoize (fn [] (slurp test-url))))

(def test-doc
  (memoize (fn [] (xpath/xml->doc (test-url-xml)))))

(defn extract-root [xpath-doc]
  (first (xpath/$x "/*" (xpath-doc))))

(defn report-text [xml-root]
  (->
    (get xml-root :text)
    (str/split #"\t+\n+")))

(defn gen-stuff-for-translate [report]
  (->>
    (mapv (fn [x] (str (str/trim x) "\n")) report)
    (filterv (complement str/blank?))))

(deftest xpath-basic
  (testing "that this might just work"
    (println
      (->
        (extract-root test-doc)
        (report-text)
        (gen-stuff-for-translate)))
    (is (not (= nil (get (extract-root test-doc) :text))))))