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
;; Search Dataset Tests
;;

;;
;; Search Granule Tests
;;

;;
;; Image Granule Tests
;;

;;
;; Extract Granule Tests
;;