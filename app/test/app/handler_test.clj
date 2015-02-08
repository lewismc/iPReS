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

(deftest no-bogus-params?-happy
  (testing "happy case"
    (is (= true (no-bogus-params? [:a :b :c] [:a :b])))))

(deftest no-bogus-params?-sad
  (testing "sad case"
    (is (= false (no-bogus-params? [:a :b :c] [:d])))))

;;
;; Metadata Dataset Tests
;;

(deftest metadata-dataset-is-valid?-happy-only-required
  (testing "happy case with only required parameters"
    (is (= true (metadata-dataset-is-valid? {:datasetId 12 :shortName "yes"})))))

(deftest metadata-dataset-is-valid?-happy-all
  (testing "happy case with all supported parameters"
    (is (= true (metadata-dataset-is-valid? {:datasetId 12 :shortName "yes" :format "no"})))))

(deftest metadata-dataset-is-valid?-sad-not-all-required
  (testing "sad case where not all required params are passed in"
    (is (= false (metadata-dataset-is-valid? {:datasetId 12 :format "no"})))))

(deftest metadata-dataset-is-valid?-sad-bogus-parameters
  (testing "sad case where a bogus parameter is pased in as an optional parameter"
    (is (= false (metadata-dataset-is-valid? {:datasetId 12 :shortName "yes" :cowboy "yeehaw"})))))

(deftest metadata-dataset-is-valid?-null
  (testing "null case"
    (is (= false (metadata-dataset-is-valid? nil)))))

;;
;; Metadata Granule Tests
;;

(deftest metadata-granule-is-valid?-happy-only-required
  (testing "happy case with only required parameters"
    (is (= true (metadata-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "what"})))))

(deftest metadata-granule-is-valid?-happy-all
  (testing "happy case with all parameters"
    (is (= true (metadata-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "what" :format "no"})))))

(deftest metadata-granule-is-valid?-sad-not-all-required
  (testing "sad case where not all required parameters are passed in"
    (is (= false (metadata-granule-is-valid? {:datasetId 12 :shortname "yes" :format "no"})))))

(deftest metadata-granule-is-valid?-sad-bogus-parameters
  (testing "sad case where a bogus parameter is passed in"
    (is (= false (metadata-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "what" :cowboy "yeehaw"})))))

(deftest metadata-granule-is-valid?-null
  (testing "null case"
    (is (= false (metadata-granule-is-valid? nil)))))

;;
;; Search Dataset Tests (these actually have no required parameters, hence the lax tests)
;;

(deftest search-dataset-is-valid?-happy
  (testing "happy case"
    (is (= true (search-dataset-is-valid? {:keyword "modis"})))))

(deftest search-dataset-is-valid?-bogus-param
  (testing "sad case - bogus parameter"
    (is (= false (search-dataset-is-valid? {:cowboy "yeehaw"})))))

(deftest search-dataset-is-valid?-null
  (testing "null case"
    (is (= false (search-dataset-is-valid? nil)))))

;;
;; Search Granule Tests
;;

(deftest search-granule-is-valid?-happy-only-required
  (testing "happy case where only required parameters are supplied"
    (is (= true (search-granule-is-valid? {:datasetId "PODAAC-ASOP2-25X01" :shortName "ASCATA-L2-25km"})))))

(deftest search-granule-is-valid?-happy-additional-params
  (testing "happy case where additional parameters are specified"
    (is (= true (search-granule-is-valid? {:datasetId "PODAAC-ASOP2-25X01" :shortName "ASCATA-L2-25km" :startIndex 1})))))

(deftest search-granule-is-valid?-sad-not-all-required-params
  (testing "sad case where not all required parameters are specified"
    (is (= false (search-granule-is-valid? {:datasetId "PODAAC-ASOP2-25X01" :startIndex 1})))))

(deftest search-granule-is-valid?-sad-bogus-param
  (testing "sad case where a bogus parameter is passed in"
    (is (= false (search-granule-is-valid? {:datasetId "PODAAC-ASOP2-25X01" :shortName "ASCATA-L2-25km" :cowboy "yeehaw"})))))

(deftest search-granule-is-valid?-nil
  (testing "null case"
    (is (= false (search-granule-is-valid? nil)))))

;;
;; Image Granule Tests
;;

(deftest image-granule-is-valid?-happy-only-required
  (testing "happy case with only the required parameters"
    (is (= true (image-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "no" :request "what"
                                          :service   "banana" :version 99.99 :format "wrapped in paper"
                                          :bbox      "what is this" :height 0 :width "acceptable"})))))

(deftest image-granule-is-valid?-happy-additional-params
  (testing "happy case where additional parameters are specified"
    (is (= true (image-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "no" :request "what"
                                          :service   "banana" :version 99.99 :format "wrapped in paper"
                                          :bbox      "what is this" :height 0 :width "acceptable" :layers "on google maps"})))))

(deftest image-granule-is-valid?-not-all-required-params
  (testing "sad case where not all required parameters are specfied"
    (is (= false (image-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "no" :request "what"
                                           :service   "banana" :version 99.99 :format "wrapped in paper"
                                           :bbox      "what is this" :height 0})))))

(deftest image-granule-is-valid?-bogus-params
  (testing "sad case with a bogus parameter added in"
    (is (= false (image-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "no" :request "what"
                                           :service   "banana" :version 99.99 :format "wrapped in paper"
                                           :bbox      "what is this" :height 0 :width "acceptable" :cowboy "yeehaw"})))))

(deftest image-granule-is-valid?-null
  (testing "null case"
    (is (= false (image-granule-is-valid? nil)))))


;;
;; Extract Granule Tests (all parameters are required)
;;

(deftest extract-granule-is-valid?-happy
  (testing "happy case"
    (is (= true (extract-granule-is-valid? {:datasetId 12 :shortName "yes" :granuleName "no"
                                            :bbox      "what is this" :format "one which is pleasing to the eyes"})))))

(deftest extract-granule-is-valid?-sad
  (testing "sad case"
    (is (= false (extract-granule-is-valid? {:datasetId 12})))))

(deftest extract-granule-is-valid?-null
  (testing "null case"
    (is (= false (extract-granule-is-valid? nil)))))
