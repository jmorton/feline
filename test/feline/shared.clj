(ns feline.shared
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [feline.config]
            [feline.system]))

(defn test-system
  "Start the app."
  ([]
   (test-system "test/feline.ini"))
  ([cfg-path]
   (try
     (log/debugf "Starting app with `%s`" cfg-path)
     (log/debugf "Configuration: %s" (feline.config/build {:ini cfg-path}))
     (-> {:ini cfg-path}
         feline.config/build
         feline.system/system)
     (catch java.io.FileNotFoundException e
       (log/error "File not found, is `test/feline.ini` on the load path?")))))

(defmacro with-system
  "Start and stop the system, useful for integration tests."
  [[binding] & body]
  `(let [~binding (component/start (test-system "test/feline.ini"))]
     (try
       (do ~@body)
       (finally
         (component/stop ~binding)))))
