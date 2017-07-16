(ns test-ring-atom.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [test-ring-atom.test-queue :as t-queue]))

(defn ok [d] {:status 200 :body d})

(defn bad-request [status message] 
  { :status status 
    :body { :error { :message message }}})

(defn wrap-exception-handling
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch clojure.lang.ExceptionInfo e
        (if (= 401 (-> e ex-data :http-status))
          (bad-request 401 (.getMessage e))
          (bad-request 400 (.getMessage e)))))))

(defn add-number [number]
  (if (not (t-queue/queue-has-id? number))
    (do 
      (t-queue/add-to-queue number)
      (Thread/sleep 60000))
    (throw (ex-info (str "Number " number " already added") {}))))

(defroutes app-routes
  (POST "/add/:number" [number] 
    (add-number number)
    (ok (str "Number " number " added" )))

  (GET "/has-number/:number" [number]
    (if (t-queue/queue-has-id? number)
      (ok (str "Queue has number " number))
      (ok (str "Queue has not number " number))))

  (route/not-found "Not Found"))

(def app
 (->  (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      wrap-exception-handling
      middleware/wrap-json-response))
