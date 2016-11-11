(defproject feline "0.1.0-SNAPSHOT"
  :description "Feline HTTP Example"
  :url "http://github.com/jmorton/feline"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;; http server
                 [ring/ring "1.5.0"]
                 [ring-jetty-component "0.3.1"]
                 [jmorton/ring-accept "0.2.0-SNAPSHOT"]
                 [jmorton/ring-problem "0.1.0-SNAPSHOT"]
                 [compojure "1.5.1"]
                 ;; json/xml/html
                 [cheshire "5.6.3"]
                 [org.clojure/data.xml "0.1.0-beta2"]
                 [enlive "1.1.6"]
                 [hiccup "1.0.5"]
                 ;; persistence
                 [com.datastax.cassandra/cassandra-driver-core "3.1.2"]
                 [cc.qbits/alia-all "3.3.0"]
                 [cc.qbits/hayt "3.2.0"]
                 ;; messaging
                 [com.novemberain/langohr "3.6.1"]
                 ;; logging
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-log4j12 "1.7.21"]
                 ;; system component library
                 [com.stuartsierra/component "0.3.1"]
                 ;; configuration
                 [jmorton/uberconf "0.1.0-SNAPSHOT"]]
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.3.0-alpha3"]
                                  [http-kit "2.2.0"]
                                  [http-kit.fake "0.2.2"]]
                   :plugins [[lein-codox "0.10.0"]
                             [lein-ancient "0.6.10"]
                             [lein-kibit "0.1.2"]
                             [jonase/eastwood "0.2.3"]]}
             :test {:dependencies [[http-kit "2.2.0"]
                                   [http-kit.fake "0.2.2"]]}
             :uberjar {:aot :all
                       :main feline.core}}
  ;; :main ^:skip-aot feline.core
  :target-path "target/%s/"
  :compile-path "%s/classes"
  :repl-options {:init-ns dev})
