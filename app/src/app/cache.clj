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
