(ns gr-interview.data
  (:require [clojure.pprint :refer [pprint]]))

(def db (atom ()))

(defn print-records [recs]
  (doseq [r recs] (pprint r)))

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