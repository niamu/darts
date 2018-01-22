(ns darts.server
  "Core server components to serve web pages"
  (:gen-class)
  (:require [darts.router :as router]
            [org.httpkit.server :as http]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defonce server (atom nil))

(def app
  "Application route handling"
  (-> #'router/route-handler
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-not-modified)))

(defn stop
  "Stop the Web server"
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil))
  (println "Stopped."))

(defn start
  "Start the Web server"
  []
  (when (nil? @server)
    (reset! server (http/run-server #'app {:port 8080})))
  (println "Started."))

(defn restart [] (stop) (start))

(defn -main [] (start))
