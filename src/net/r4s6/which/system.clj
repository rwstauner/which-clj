(ns net.r4s6.which.system
  (:require [clojure.java.io :as io])
  (:import java.nio.file.Files))

(defn env-path [] (System/getenv "PATH"))
(def path-separator (System/getProperty "path.separator"))

(defn path-join [& args]
  (->> args
       (apply io/file)
       str))

(defn exists? [p]
  (-> p
      (io/file)
      (.exists)))

(defn executable? [p]
  (-> p
      (io/file)
      (.toPath)
      (Files/isExecutable)))
