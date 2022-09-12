(ns kyleerhabor.bushel.core
  (:refer-clojure :exclude [merge])
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io])
  (:import java.io.PushbackReader))

(defn read-edn 
  ([x] (read-edn {} x))
  ([x opts] (edn/read opts (PushbackReader. x))))

(defn src->edn
  ([src] (src->edn src {}))
  ([src opts]
   (with-open [r (io/reader src)]
     (read-edn r opts))))

(defn merge [maps]
  (apply merge-with into maps))

(defn config
  ([srcs] (config srcs {}))
  ([srcs opts]
   (merge (map #(if (coll? %)
                  ;; Already good.
                  %
                  (src->edn % opts)) srcs))))
