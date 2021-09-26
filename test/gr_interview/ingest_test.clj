(ns gr-interview.ingest-test
  (:require [clojure.test :refer :all]
            [gr-interview.ingest :refer [process-line ingest-file]]
            [gr-interview.data :refer [db]]
            [clojure.java.io :as io]))

;; Some test data
(def bar-line "Smith | Steve | steve@nowhere.com | Blue | 1979-05-02")
(def comma-line "Smith, Steve, steve@nowhere.com, Blue, 1979-05-02")
(def space-line "Smith Steve steve@nowhere.com Blue 1979-05-02")

(deftest test-process-line
  (let [rec-bar (process-line bar-line)
        rec-comma (process-line comma-line)
        rec-space (process-line space-line)]

    (is (= (:LastName rec-bar) "Smith"))
    (is (= (:FirstName rec-comma) "Steve"))
    (is (= (:Email rec-space) "steve@nowhere.com"))
    (is (= (:FavoriteColor rec-bar) "Blue"))
    (is (= (.toString (:DateOfBirth rec-comma)) "1979-05-02"))
    (is (= rec-bar rec-comma rec-space))))

(deftest test-file-ingest
  (reset! db ())
  (ingest-file (io/resource "test1.bsv"))
  (is (= 4 (count @db)))
  (is (= 1 (count (filter #(= (:FirstName %) "Steve") @db))))
  (is (= 1 (count (filter #(= (:FirstName %) "Hari") @db)))))