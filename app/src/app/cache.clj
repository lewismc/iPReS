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

(ns app.cache
  (:require [clojure.core.cache :as cache]))

(def ^:private data-cache (atom nil))

(defn clear
  "Clears the data cache, reseting it to an empty atom."
  []
  (reset! data-cache (cache/lru-cache-factory {})))

;; Initialize the cache here to make it easier to work with.
(clear)

(defn cache-add
  "Adds a given key and value to the cache.

  Internally, this returns a new instance of the cache
  with the added key and value, then swaps the reference
  of the atom to refer to this new cache."
  [key val]
  (swap! data-cache cache/miss key val))

(defn cache-has?
  "Returns true if the cache contains the given key; otherwise, false."
  [key]
  (cache/has? @data-cache key))

(defn cache-lookup
  "Returns the item coresponding to the given key; otherwise, nil."
  [key]
  (cache/lookup @data-cache key))

(defn cache-drop-key
  "Drops the given key and its associated value from the cache.

  Internally, this returns a new instance of the cache
  with the specified key and its associated value removed,
  then swaps the reference of the atom to refer to this new cache."
  [key]
  (swap! data-cache cache/evict key))
