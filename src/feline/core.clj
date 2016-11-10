(ns feline.core
  "Entrypoint for app."
  (:require [com.stuartsierra.component :as component]
            [feline.config]
            [feline.system]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn -main
  "Start the app."
  [& args]
  (try
    (log/debugf "Starting app with `feline.ini`")
    (-> args
        feline.config/build
        feline.system/system
        component/start)
    (catch java.io.FileNotFoundException e
      (log/error "File not found, is `feline.ini` on the load path?"))))
