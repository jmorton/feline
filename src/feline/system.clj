(ns feline.system
  "Define components and function for building a system"
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :as server]
            [clojure.tools.logging :as log]
            [qbits.alia :as alia]
            [langohr.core :as rmq]
            [feline.app]))

;;;

(defrecord Database [config]
  component/Lifecycle
  (start [component]
    (log/info "starting db component ...")
    (->> (:database config)
         (apply alia/cluster)
         (assoc component :cluster)))
  (stop [component]
    (log/info "stopping db component ...")
    (alia/shutdown (:cluster component))
    (dissoc component :cluster)))

(defn new-database [config]
  (map->Database {:config config}))

;;;

(defrecord Event [config]
  component/Lifecycle
  (start [component]
    (log/info "starting event component ...")
    (->> (:event config)
         (rmq/connect)
         (assoc component :connection)))
  (stop [component]
    (log/info "stopping event component ...")
    (rmq/close (:connection component))
    (dissoc component :connection)))

(defn new-event [config]
  (->Event config))

;;;

(defrecord App [config db msg]
  component/Lifecycle
  (start [component]
    (log/info "starting app component ...")
    (assoc component :handler (feline.app/new-handler db msg)))
  (stop [component]
    (log/info "stopping app component ...")
    (dissoc component :handler)))

(defn new-app [config]
  (map->App {:config config :db nil :msg nil}))

;;;

(defrecord HTTP [config app]
  component/Lifecycle
  (start [component]
    (log/infof "starting http component ...")
    (log/infof "http listening on port %s" (get-in config [:http :port]))
    (assoc component :server (server/run-server (:handler app)
                                                (:http config))))
  (stop [component]
    (let [stop-fn (:server component)]
      (log/info "stopping http component ...")
      (stop-fn)
      (dissoc component :server))))

(defn new-http [config]
  (map->HTTP {:config config}))

;;;

(defrecord Logging [config]
  component/Lifecycle
  (start [component]
    component)
  (stop [component]
    component))

(defn new-logger [config]
  (map->Logging {:config config}))

;;;

(defn system [cfg]
  (component/system-map
   :log  (new-logger cfg)
   :db   (new-database cfg)
   :msg  (new-event cfg)
   :app  (component/using (new-app cfg) [:db :msg])
   :http (component/using (new-http cfg) [:app])))
