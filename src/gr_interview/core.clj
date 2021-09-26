(ns gr-interview.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [gr-interview.ingest :refer [ingest-file]]
            [gr-interview.data :refer [db print-records-sorted]])
  (:gen-class))

(defn -main
  "Main entry point"
  [& filenames]
  (println filenames)
  (doseq [file filenames] (ingest-file file))
  (print-records-sorted))