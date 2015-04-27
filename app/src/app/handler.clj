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

(ns app.handler
  (:use compojure.core)
  (:use [ring.util.response :only [response]]
        [ring.middleware.json :only [wrap-json-response]]
        [ring.middleware.logger :only [wrap-with-logger]])

  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [app.core :as core]))

(defn is-supported?
  "Return true if a given language code, specified as a string,
  is a key in the supported map of languages."
  [lang-code]
  (->>
    (keyword lang-code)
    (contains? core/langs)))

(defn no-bogus-params?
  "Returns true if all of the supplied URL parameters
  are valid as defined by their respective PO.DAAC service.

  supported-params:
  The collection of supported parameters for a particular PO.DAAC service call.

  params:
  The collection of URL parameters entered."
  [supported-params given-params]
  (every? (set supported-params) given-params))

(defn metadata-dataset-is-valid?
  "Returns true if a request for an iPReS Metadata Dataset
  supplies the required parameters and no bogus parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/metadata/dataset/index.html"
  [request]
  (let [supported-params [:datasetId :shortName :format]]
    (and (or (contains? request :datasetId)
             (contains? request :shortName))
         (no-bogus-params? supported-params (keys request)))))

(defn metadata-granule-is-valid?
  "Returns true if a request for an iPRes Metadata Granule
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/metadata/granule/index.html"
  [request]
  (let [supported-params [:datasetId :shortName :granuleName :format
                          :startTime :endTime :itemsPerPage]]
    (and (contains? request :datasetId)
         (contains? request :shortName)
         (or
           ;; Request is for a metadata for a single, specified granule
           (and (contains? request :granuleName)
                (complement (contains? request :format)))

           ;; Request is for metadata for a list of granules archived
           ;; within the last 24 hours in Datacasting format
           (and (contains? request :format)
                (complement (contains? request :granuleName)))

           ;;Request is for metadata for a list of granules
           (and (contains? request :startTime)
                (contains? request :endTime)
                (contains? request :format)))
         (no-bogus-params? supported-params (keys request)))))


(defn search-dataset-is-valid?
  "Returns true if a request for an iPReS Search Dataset
  supplies the appropriate parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/search/dataset/index.html"
  [request]
  (let [supported-params [:keyword :startTime :endTime :startIndex
                          :itemsPerPage :datasetId :shortName :instrument
                          :satellite :fileFormat :status :processLevel
                          :pretty :format :sortBy :bbox :full]]
    (and (not (= request nil))
         (no-bogus-params? supported-params (keys request)))))

(defn search-granule-is-valid?
  "Returns true if a request for an iPReS Search Granule
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/search/granule/index.html"
  [request]
  (let [supported-params [:datasetId :shortName :startTime :endTime
                          :bbox :startIndex :itemsPerPage :sortBy
                          :format :pretty]]
    (and (or (contains? request :datasetId)
             (contains? request :shortName))
         (no-bogus-params? supported-params (keys request)))))

(defn extract-granule-is-valid?
  "Returns true if a request for an iPReS Extract Granule
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/extract/granule/index.html"
  [request]
  (let [required-params [:datasetId :shortName :granuleName :bbox :format]]
    (every? (set (keys request)) required-params)))

(defn api-routes
  "The API routes for the iPReS service.
  Nested route structure under supported languages."
  [lang]
  (routes
    (GET "/metadata/dataset" [& request]
         (let [req (dissoc request :lang)]
           (if (metadata-dataset-is-valid? req)
             (response {:extracted-text (core/translate-request
                                         "metadata/dataset" req lang "")}))))
    (GET "/metadata/granule" [& request]
         (let [req (dissoc request :lang)]
           (if (metadata-granule-is-valid? req)
             (response (core/translate-request
                        "metadata/granule" req lang "")))))
    (GET "/search/dataset" [& request]
         (let [req (dissoc request :lang)]
           (if (search-dataset-is-valid? req)
             (response (core/translate-request
                        "search/dataset" req lang "")))))
    (GET "/search/granule" [& request]
         (let [req (dissoc request :lang)]
           (if (search-granule-is-valid? req)
             (response (core/translate-request
                        "search/granule" req lang "")))))
    ;;(GET "/extract/granule" [& request]
    ;;     (let [req (dissoc request :lang)]
    ;;       (if (extract-granule-is-valid? req)
    ;;         (response (core/translate-request
    ;;                    "extract/granule" req lang "")))))
    (route/not-found
      (response "Not found."))))

;; iPReS routes - 404's if language unsupported
(defroutes ipres
           (context "/:lang" [lang]
                    (if (is-supported? lang)
                      (api-routes lang)
                      (response "Not found."))))

(def app
  (->
    (handler/api ipres)
    (wrap-with-logger)
    ;; this code will go away eventually ... maybe
    (wrap-json-response)))
