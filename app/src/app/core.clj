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

(ns app.core
  (:require
    [app.cache :refer :all]
    [clojure.string :as str]
    [clj-xpath.core :as xpath]
    [clj-xpath.lib :as lib]
    [ring.util.codec :as codec])
    (:import (org.apache.tika.language.translate MicrosoftTranslator)))

(def langs {:en       "english"
            :ar       "arabic"
            :bg       "bulgarian"
            :ca       "catalan"
            :cs       "czech"
            :da       "danish"
            :nl       "dutch"
            :et       "estonian"
            :fi       "finnish"
            :fr       "french"
            :de       "german"
            :el       "greek"
            :he       "hebrew"
            :hi       "hindi"
            :mww      "hmong daw"
            :id       "indonesian"
            :it       "italian"
            :ja       "japanese"
            :hu       "hungarian"
            :ko       "korean"
            :lv       "latvian"
            :lt       "lithuanian"
            :ms       "malay"
            :mt       "maltese"
            :no       "norwegian"
            :fa       "persian"
            :pl       "polish"
            :pt       "portuguese"
            :ro       "romanian"
            :ru       "russian"
            :sk       "slovak"
            :sl       "slovenian"
            :es       "spanish"
            :sv       "swedish"
            :th       "thai"
            :tr       "turkish"
            :uk       "ukrainian"
            :ur       "urdu"
            :vi       "vietnamese"
            :cy       "welsh"
            :zh-CHS   "chinese simplified"
            :zh-CHT   "chinese traditional"
            :ht       "haitian creole"
            :tlh      "klingon"
            :tlh-Qaak "klingon (plqaD)"})

;;;;;;;;;;
;;
;; Dealing with PO.DAAC region
;;
;;;;;;;;;;

(def podaac-base-url "https://podaac.jpl.nasa.gov/ws/")
(def source-language "en")

(defn- build-url
  "Returns a fully-qualifies PO.DAAC route based on
  the given route and parameters."
  [route params]
  (str podaac-base-url route "?" (codec/form-encode params)))

(defn- fetch-xml
  "Fetches and caches XML from a PO.DAAC route."
  [url]
  (slurp url))

(defn- build-xdoc
  "Builds an XPATH-accessible XML document from
  a given, formatted XML document."
  [xml]
  ;; Avoids [Fatal Error] :1:10: DOCTYPE is disallowed when the feature 
  ;; "http://apache.org/xml/features/disallow-doctype-decl" set to true.
  ;; org.xml.sax.SAXParseException: DOCTYPE is disallowed when the feature 
  ;; "http://apache.org/xml/features/disallow-doctype-decl" set to true.
  (let [opts {:disallow-doctype-decl false}]
    (try (xpath/xml->doc xml opts))))

(defn- extract-root
  "Extracts the root of the XML document using an XPATH query."
  [xpath-doc]
  (first (xpath/$x "/*" xpath-doc)))

(defn- extract-relevant-text
  "Returns a sequence of relevant phrases from the XPATH root,
  split by the tab and newline delimiters."
  [root]
  (->
    (get root :text)
    (str/split #"\t+\n+")))

(defn- format-relevant-text
  "Returns a sequence of chunks of relevant text,
  stripped of all leading and trailing whitespace."
  [text]
  (->>
    (map (fn [x] (str/trim x)) text)
    (filter (complement str/blank?))))

(defn hit-podaac
  "Hits the PO.DAAC web service specified by the given route,
  with the parameters specified by the given params."
  [route params]
  (->
    (build-url route params)
    (fetch-xml)
    (build-xdoc)
    (extract-root)
    (extract-relevant-text)
    (format-relevant-text)))

;;;;;;;;;;
;;
;; Translation region
;;
;;;;;;;;;;
(def translator (MicrosoftTranslator.))

(defn translate-with-tika
  "Returns the translated dataset into the specified language
  using Apache Tika."
  [dataset lang-code]
  ;; Logging to console for requirements proving
  (println "\n\n\n\n\nDEBUG: Attempting to translate by calling tika-translate...\n\n\n\n\n")
  (pmap (fn [x] (.translate translator (str/replace x #"\\/" "/") source-language lang-code)) dataset))

(defn translate-to-lang
  "Returns PO.DAAC dataset specified by the given language."
  [dataset key lang]
  (cache-add key (translate-with-tika dataset lang))
  (cache-lookup key))

;;;;;;;;;;
;;
;; Top-level region
;;
;;;;;;;;;;

(defn- route-to-key
  "Returns the concatenation of the given route, split by slashes,
  with the suppled language code."
  [route lang-code]
  (keyword (str (apply str (str/split route #"/")) lang-code)))

(defn translate-request
  "Handles a given iPres request and returns the translated data in the specified format."
  [route params lang-code format]
  (let [cache-key (route-to-key route lang-code)]
    (if (cache-has? cache-key)
      (cache-lookup cache-key)
      (->
        (hit-podaac route params)
        (translate-to-lang cache-key lang-code)
        ;;(convert-to-format format)
        ))))
