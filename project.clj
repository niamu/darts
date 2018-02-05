(defproject darts "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure        "1.9.0"]
                 [org.clojure/clojurescript  "1.9.946"]
                 [com.domkm/silk             "0.1.2"]
                 [garden                     "1.3.2"]
                 [hiccup                     "1.0.5"]
                 [http-kit                   "2.2.0"]
                 [ring/ring-core             "1.4.0"]
                 [ring/ring-defaults         "0.2.1"]
                 [rum                        "0.10.8"]]
  :main darts.server
  :profiles {:uberjar {:aot :all
                       :uberjar-name "darts.jar"
                       :prep-tasks ["compile" ["cljsbuild" "once"]]}
             :dev {:plugins [[lein-cljsbuild "1.1.7"]
                             [lein-figwheel  "0.5.14"]]}}
  :clean-targets ^{:protect false} ["resources/public/js" "target"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main darts.router
                                   :asset-path "/js/dev/out"
                                   :output-to "resources/public/js/darts.js"
                                   :output-dir "resources/public/js/dev/out"
                                   :optimizations :none
                                   :parallel-build true
                                   :source-map-timestamp true}}
                       {:id "main"
                        :source-paths ["src"]
                        :compiler {:main darts.router
                                   :output-to "resources/public/js/darts.js"
                                   :optimizations :advanced
                                   :parallel-build true
                                   :source-map-timestamp true}}]})
