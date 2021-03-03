(ns net.r4s6.which-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.string :as string]
            #?(:cljs [cljs.nodejs :as node])
            [net.r4s6.which :as w]
            [net.r4s6.which.system :as sys]))

(def cwd #?(:clj (System/getProperty "user.dir")
            :cljs (.cwd node/process)))
(def test-paths (map (partial w/path-join cwd "test" "paths")
                     ["no-bin" "bin1" "bin2"]))
(def bin1 (second test-paths))
(def bin2 (nth test-paths 2))

(def test-path (string/join sys/path-separator test-paths))

(deftest which-test
  (with-redefs [sys/env-path (constantly test-path)]
    (testing "which finds exec"
      (let [base "which-test-exec"]
        (is (= (w/path-join bin2 base)
               (w/which base)))))
    (testing "which does not find non exec"
      (let [base "which-not-exec"]
        (is (= nil
               (w/which base)))))
    (testing "find-in finds any"
      (let [base "which-not-exec"]
        (is (= (w/path-join bin1 base)
               (w/find-in (w/system-path) base)))))
    (testing "empty paths"
      (let [base "any"]
        (is (= nil
               (w/find-in [] base)))))))
