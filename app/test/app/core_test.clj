(ns app.core-test
  (:require [clojure.test :refer :all]
            [app.core :refer :all]
            [app.cache :as cache]))

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
           (convert-to-format "<foo><bar><baz>The baz value</baz></bar></foo>" "xml")))))

(deftest basic-client-get-test
  (testing "temp"
    (is (= \< (get (hit-podaac "metadata/dataset"
                           {:datasetId "PODAAC-GHMG2-2PO01"
                            :shortName "OSDPD-L2P-MSG02"}) :body)))))