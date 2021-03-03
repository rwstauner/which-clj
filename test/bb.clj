#!/usr/bin/env bb

(require '[clojure.test :as t]
         '[babashka.classpath :as cp])

(cp/add-classpath "src:test")

(require 'net.r4s6.which-test)

(def test-results
  (t/run-tests 'net.r4s6.which-test))

(def failures-and-errors
  (let [{:keys [:fail :error]} test-results]
    (+ fail error)))

(System/exit failures-and-errors)
