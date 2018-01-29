(ns darts.router
  (:require [darts.page :as page]
            [domkm.silk :as silk]
            [rum.core :as rum]
            #?@(:clj
                [[domkm.silk.serve :refer [ring-handler]]])))

(def routes
  (silk/routes {:dashboard  [[]]
                :stylesheet [["css" "screen.css"]]}))

(defn serve
  [{:keys [status headers body]}]
  #?(:clj (fn [request]
            {:status (or status 200)
             :headers (or headers {"Content-Type" "text/html"})
             :body (page/wrap (body))})
     :cljs (rum/mount (body) (.getElementById js/document "app"))))

(defmulti response identity)

(defmethod response :default
  [route]
  (serve {:body #'page/board}))

(defn route->response
  [matched-route]
  (response matched-route))

#?(:clj
   (def route-handler
     (ring-handler routes route->response)))

#?(:cljs
   (defn path->name
     [url]
     (:domkm.silk/name (silk/arrive routes url))))

#?(:cljs (route->response :dashboard))
