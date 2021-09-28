(ns gr-interview.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [gr-interview.ingest :refer [ingest-file]]
            [gr-interview.data :refer [db print-records-sorted]]
            [gr-interview.handler :refer [routes]]
            [muuntaja.core :as m]
            [reitit.ring :as ring]
            [ring.middleware.params :as params]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.coercion :as coercion]
            [clojure.walk :refer [keywordize-keys]])
  (:gen-class))

(def app
  (ring/ring-handler
    (ring/router
      [routes]
      {:data {:muuntaja   m/instance
              :middleware [params/wrap-params
                           muuntaja/format-middleware
                           coercion/coerce-exceptions-middleware
                           coercion/coerce-request-middleware
                           coercion/coerce-response-middleware]}})
    (ring/create-default-handler)))

(defn start [opts]
  (run-jetty #'app {:port 4242 :join? false})
  (println (str "server running on port " 4242 )))

(defn -main
  "Main entry point"
  [& filenames]
  (println filenames)
  (doseq [file filenames] (ingest-file file))
  (print-records-sorted))