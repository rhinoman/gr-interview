(ns gr-interview.handler-test
  (:require [clojure.test :refer :all]
            [gr-interview.data :refer [db]]
            [gr-interview.core :refer [app]]
            [reitit.ring :as ring]
            [clojure.java.io :refer [reader writer input-stream]]
            [cheshire.core :as cheshire]
            [clojure.walk :refer [keywordize-keys]]))

(def test-record-2 "Seldon Hari hari@foundation.net Gray 1988-01-01")
(def test-record-3 "Landale | Alis | alis@palma.net | Red | 1987-12-20")
(def test-record-1 "Smith, Steve, steve@nowhere.com, Blue, 1979-05-02")
(def test-record-4 "Burton | Levar | levar@burton.com | Red | 1957-02-16")
(def test-records [test-record-1 test-record-2 test-record-3 test-record-4])


(defn decode-response-body [response]
  (keywordize-keys (cheshire/decode (slurp (reader (:body response))))))

(deftest handler-test
  (reset! db [])
  (testing "router is not nil"
    (is (some? (ring/get-router app))))
  (testing "I can create a few records"
    (doseq [record test-records]
      (let [response (app {:uri            "/records"
                           :request-method :post
                           :body           record})
            body (decode-response-body response)]
        (is (= (:status response) 201))
        (is (some? body)))))
  (testing "returns a list sorted by color"
    (let [response (app {:uri "/records/color"
                         :request-method :get})
          results (decode-response-body response)]
      (is (= 4 (count results)))
      (is (= "Blue" (:FavoriteColor (first results))))
      (is (= "Red" (:FavoriteColor (last results))))))
  (testing "returns a list sorted by birthdate"
    (let [response (app {:uri "/records/birthdate"
                         :request-method :get})
          results (decode-response-body response)]
      (is (= 4 (count results)))
      (is (= "1957-02-16" (:DateOfBirth (first results))))
      (is (= "1988-01-01" (:DateOfBirth (last results))))))
  (testing "returns a list sorted by last name"
    (let [response (app {:uri "/records/name"
                         :request-method :get})
          results (decode-response-body response)]
      (is (= 4 (count results)))
      (is (= "Burton" (:LastName (first results))))
      (is (= "Smith" (:LastName (last results)))))))
