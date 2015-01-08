(ns app.handler
  (:use compojure.core)
  (:use [ring.middleware.json])
  (:use [ring.util.response :only [response]])

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
     (and (contains? request :datasetId)
          (contains? request :shortName)
          (no-bogus-params? supported-params (keys request)))))

(defn metadata-granule-is-valid?
  "Returns true if a request for an iPRes Metadata Granule
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/metadata/granule/index.html"
  [request]
  (let [supported-params [:datasetId :shortName :granuleName :format]]
    (and (contains? request :datasetId)
         (contains? request :shortName)
         (contains? request :granuleName)
         (no-bogus-params? supported-params (keys request)))))


(defn search-dataset-is-valid?
  "Returns true if a request for an iPReS Search Dataset
  supplies the requried parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/search/dataset/index.html"
  [request]
  (let [supported-params [:keyword :startTime :endTime :startIndex
                          :itemsPerPage :datasetId :shortName :instrument
                          :satellite :fileFormat :status :processLevel
                          :pretty :format :sortBy :bbox :full]]
    (no-bogus-params? supported-params (keys request))))

(defn search-granule-is-valid?
  "Returns true if a request for an iPReS Search Granule
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/search/granule/index.html"
  [request]
  (let [supported-params [:datasetId :shortName :startTime :endTime
                          :bbox :startIndex :itemsPerPage :sortBy
                          :format :pretty]]
    (and (contains? request :datasetId)
         (contains? request :shortName)
         (no-bogus-params? supported-params (keys request)))))

(defn image-granule-is-valid?
  "Returns true if a request for an iPReS Image Granule
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/image/granule/index.html"
  [request]
  (let [supported-params [:datasetId :shortName :granuleName :request
                          :service :version :format :bbox
                          :height :width :layers :style :srs]]
    (and (contains? request :datasetId)
         (contains? request :shortName)
         (contains? request :granuleName)
         (contains? request :request)
         (contains? request :service)
         (contains? request :version)
         (contains? request :format)
         (contains? request :bbox)
         (contains? request :height)
         (contains? request :width)
         (no-bogus-params? supported-params request))))

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
         (if (metadata-dataset-is-valid? request)
           (response {:msg (str "a data set for " lang)})
           (response {:msg "you are a bad man"})))
    (GET "/metadata/granule" [& request]
         (if (metadata-granule-is-valid? request)
           (response {:msg (str "a granule for " lang)})
           (response {:msg "no kitty that's a bad kitty"})))
    (GET "/search/dataset" [request]
         (response {:msg "hello"}))
    (GET "/search/granule" [request]
         (response {:msg "hello"}))
    (GET "/image/granule" [request]
         (response {:msg "hello"}))
    (GET "/extract/granule" [request]
         (response {:msg "hello"}))
    (route/not-found
      (response {:msg "Page not found"}))))

;; iPReS routes - 404's if language unsupported
(defroutes ipres
           (context "/:lang" [lang]
                    (if (is-supported? lang)
                      (api-routes lang))))

(def app
  (->
    (handler/api ipres)

    ;; This will need replacement...
    (wrap-json-response)

    ;; As will this
    (wrap-json-body)))
