(ns gr-interview.ingest
  (:require [gr-interview.data :refer [db]]
            [clojure.java.io :as io]
            [clojure.string :refer [split trim]])
  (:import (java.time LocalDate)
           (java.io FileNotFoundException)))

;; Pattern to match for delimiters
;; pipe, comma, or space
;; I suppose this would allow you to mix delimiters in the same file
(def delim-pattern #" \| |, | ")

(defn process-line
  "Processes a single record"
  [^String line]
  (let [rec (mapv trim (split line delim-pattern))]
    {:LastName      (get rec 0)
     :FirstName     (get rec 1)
     :Email         (get rec 2)
     :FavoriteColor (get rec 3)
     :DateOfBirth   (LocalDate/parse (get rec 4))}))

(defn ingest-line
  "Processes a single line (record) from an input file"
  [^String line]
  (let [rec (process-line line)]
    (swap! db conj rec)))

(defn ingest-file
  "Ingest a whole file"
  [filename]
  (try
    (with-open [rdr (io/reader filename)]
      (doseq [line (line-seq rdr)]
        (if (not (.isEmpty line)) (ingest-line line))))
    (catch FileNotFoundException fnfe
      (println (str "File " filename " not found")))))