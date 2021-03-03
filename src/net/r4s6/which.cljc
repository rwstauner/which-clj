(ns net.r4s6.which
  (:refer-clojure :exclude [exists?])
  (:require [clojure.string :as string]
            [net.r4s6.which.system :as sys]))

(def exists? sys/exists?)
(def executable? sys/executable?)
(def path-join sys/path-join)

(def path-separator
  "The system PATH separator (\":\" on unix, \";\" on windows)."
  sys/path-separator)

(def path-separator-re (re-pattern path-separator))

(defn system-path
  "The PATH from the environment."
  []
  (-> (sys/env-path)
      (clojure.string/split path-separator-re)))

(defn find-in
  "Finds file in provided paths."
  ([paths target] (find-in paths target exists?))
  ([paths target check]
   (loop [ps paths]
     (when-let [p (first ps)]
       (let [f (path-join p target)]
         (if (check f)
           f
           (let [more (rest ps)]
             (when (seq more)
               (recur more)))))))))

(defn which
  "Finds provided executable name in PATH or returns nil."
  [exe]
  (find-in (system-path) exe executable?))
