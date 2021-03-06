(defproject tic-tac-toe "0.1.0-SNAPSHOT"

  :description "TicTacToe on steroids"

  :url "https://github.com/whysoserious/tic-tac-toe-on-steroids"

  :source-paths ["src/clj" "src/cljs" "test/cljs"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [reagent "0.5.0"]
                 [org.clojure/clojurescript "0.0-3115" :scope "provided"]
                 [com.cemerick/piggieback "0.1.5"]
                 [weasel "0.6.0"]
                 [ring "1.3.2"]
                 [ring/ring-defaults "0.1.3"]
                 [prone "0.8.1"]
                 [compojure "1.3.2"]
                 [selmer "0.8.2"]
                 [environ "1.0.0"]
                 [leiningen "2.5.1"]
                 [figwheel "0.1.6-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-environ "1.0.0"]
            [lein-ring "0.9.0"]
            [lein-asset-minifier "0.2.2"]]

  :ring {:handler tic-tac-toe.handler/app
         :uberwar-name "tic-tac-toe.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "tic-tac-toe.jar"

  :main tic-tac-toe.server

  :clean-targets ^{:protect false} ["resources/public/js" "resources/test"]

  :minify-assets {:assets
                  {"resources/public/css/site.min.css" "resources/public/css/site.css"}}

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns tic-tac-toe.handler
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :dependencies [[ring/ring-devel "1.3.2"]
                                  [pjstadig/humane-test-output "0.6.0"]]

                   :plugins [[lein-figwheel "0.2.0-SNAPSHOT"]
                             [com.cemerick/clojurescript.test "0.3.3"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :ring-handler tic-tac-toe.handler/app}

                   :env {:dev? true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:source-map true}}
                                        :test {:source-paths ["src/cljs" "test/cljs"]
                                               :compiler {:output-to     "resources/test/js/test.js"
                                                          :output-dir    "resources/test/js/out"
                                                          :optimizations :advanced
                                                          :hashbang false
                                                          :target :nodejs
                                                          :pretty-print true}}}
                               :test-commands {"nodejs" ["node" :node-runner
                                                         "resources/test/js/test.js"]}}}

             :uberjar {:hooks [leiningen.cljsbuild minify-assets.plugin/hooks]
                       :env {:production true}
                       :aot :all
                       :omit-source true
                       :cljsbuild {:jar true
                                   :builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}

             :production {:ring {:open-browser? false
                                 :stacktraces?  false
                                 :auto-reload?  false}}})
