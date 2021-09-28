(ns gr-interview.data
  (:require [clojure.pprint :refer [pprint]])
  (:import (java.time.format DateTimeFormatter FormatStyle)))

(def db (atom ()))


(def date-formatter (DateTimeFormatter/ofPattern "M/d/YYYY"))
(defn format-record [rec]
  (str (:LastName rec) ", "
       (:FirstName rec) ", "
       (:Email rec) ", "
       (:FavoriteColor rec) ", "
       (.format (:DateOfBirth rec) date-formatter)))

(defn print-records [recs]
  (doseq [r recs] (println (format-record r))))

(defn by-fc-ln [x y]
  (compare [(:FavoriteColor x) (:LastName x)]
           [(:FavoriteColor y) (:LastName y)]))

(defn print-records-sorted []
  (let [recs1 (sort by-fc-ln @db)
        recs2 (sort-by :DateOfBirth @db)
        recs3 (reverse (sort-by :LastName @db))]
    (println "**** Sorted by Favorite Color, then Last Name ascending ****")
    (print-records recs1)
    (println "**** Sorted by Date Of Birth ascending ****")
    (print-records recs2)
    (println "**** Sorted by Last Name descending ****")
    (print-records recs3)))