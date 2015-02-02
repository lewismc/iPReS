(ns app.core-test
  (:require [clojure.test :refer :all]
            [app.core :refer :all]))

(deftest fail
  (testing "failing test so that data set prints to screen"
    (is (= nil (hit-podaac "metadata/granule/"
                           {:datasetId   "PODAAC-GHMG2-2PO01"
                            :shortName   "OSDPD-L2P-MSG02"
                            :granuleName "20120912-MSG02-OSDPD-L2P-MSG02_0200Z-v01.nc"
                            :format      "iso"})))))