(ns net.r4s6.which.system
  (:refer-clojure :exclude [exists?])
  (:require [cljs.nodejs :as node]))

(defn env-path [] (.-PATH (.-env node/process)))
(def fs (node/require "fs"))
(def path (node/require "path"))
(def windows? (-> (node/require "os")
                  (.platform)
                  (->> (re-find #"^win"))))

(def path-separator (if windows? ";" ":"))

(defn path-join [& args]
  (apply js-invoke path "join" args))

(defn exists? [p]
  (.existsSync fs p))

(defn executable? [p]
  ; statSync will throw if p doesn't exist.
  (and (exists? p)
       (-> (.statSync fs p)
           (.-mode)
           (bit-and 0111)
           (not= 0))))
