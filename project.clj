(defproject feline "0.1.0-SNAPSHOT"
  :description "Feline HTTP Example"
  :url "http://github.com/jmorton/feline"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha13"]
                 ;; http
                 [http-kit "2.1.18"]
                 [http-kit.fake "0.2.1"]
                 [compojure "1.5.1"]
                 [ring/ring "1.5.0"]
                 [jmorton/ring-accept "0.2.0-SNAPSHOT"]
                 [jmorton/ring-problem "0.1.0-SNAPSHOT"]
                 ;; json/xml/html
                 [cheshire "5.6.3"]
                 [org.clojure/data.xml "0.1.0-beta2"]
                 [enlive "1.1.6"]
                 [hiccup "1.0.5"]
                 ;; persistence
                 [com.datastax.cassandra/cassandra-driver-core "3.1.0"]
                 [cc.qbits/alia-all "3.1.11"]
                 [cc.qbits/hayt "3.0.1"]
                 ;; messaging
                 [com.novemberain/langohr "3.6.1"]
                 ;; logging
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.7"]
                 ;; system component library
                 [com.stuartsierra/component "0.3.1"]
                 ;; configuration
                 [uberconf "0.1.0-SNAPSHOT"]]
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.3.0-alpha3"]]
                   :plugins [[lein-codox "0.10.0"]
                             [lein-ancient "0.6.10"]
                             [lein-kibit "0.1.2"]
                             [jonase/eastwood "0.2.3"]]}
             :uberjar {}}
  :repl-options {:init-ns dev}
  :main feline.core)
