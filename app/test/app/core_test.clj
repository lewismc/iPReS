(ns app.core-test
  (:require [clojure.test :refer :all]
            [app.core :refer :all]
            [app.cache :as cache]))

(deftest translate-request-test
  (testing "basic coverage of translate-request with empty cache"
    (with-redefs-fn {#'hit-podaac (fn [route params] "bananas")}
                    #(is (= "bananas" (translate-request "metadata/dataset"
                                                         {:datasetId "PODAAC-GHMG2-2PO01"
                                                          :shortName "OSDPD-L2P-MSG02"}
                                                         "kr"
                                                         "xml"))))
    (is (= true (cache/cache-has? (keyword "metadatadatasetkr"))))
    (is (= "bananas" (cache/cache-lookup (keyword "metadatadatasetkr")))))

  (testing "basic coverage of translate-request with cache that has a key"
    (is (= (cache/cache-lookup (keyword "metadatadatasetkr")) (translate-request "metadata/dataset"
                                                                                 {:datasetId "PODAAC-GHMG2-2PO01"
                                                                                  :shortName "OSDPD-L2P-MSG02"}
                                                                                 "kr"
                                                                                 "xml")))))
