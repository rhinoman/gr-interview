(ns gr-interview.handler
  (:require [gr-interview.data :refer [db]]
            [gr-interview.ingest :refer [ingest-line]]))

(defn ingest-body [body]
  (try
    {:status 201
     :body (ingest-line body)}
    (catch Exception ex
      (.printStackTrace ex)
      {:status 400 ;; Let's assume the client did something wrong
       :body {:error "No, try again!"}})))

(defn create-record-handler [params]
  (let [b-input (:body params)
        ;; Checks if body is a plain string to facilitate unit tests
        ;; Otherwise, body is going to be an input stream
        body (if (string? b-input) b-input (slurp b-input))]
    (ingest-body body)))

(defn color-handler [params]
  {:status 200
   :body (sort-by :FavoriteColor @db)})

(defn birthdate-handler [params]
  {:status 200
   :body (sort-by :DateOfBirth @db)})

(defn name-handler [params]
  {:status 200
   :body (sort-by :LastName @db)})

(def routes
  ["/records"
   ["" {:post create-record-handler}]
   ["/color" {:get color-handler}]
   ["/birthdate" {:get birthdate-handler}]
   ["/name" {:get name-handler}]])