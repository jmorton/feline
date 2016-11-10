(ns feline.config
  "Configuration!"
  (:require [uberconf.core :as uberconf]
            [schema.core :as schema]))

(def config-schema
  {:http     {:port schema/Num}
   :database {:contact-points [schema/Str]}
   schema/Keyword schema/Any})

(defn build [{:keys [ini] :or {ini "feline.ini"} :as args}]
  (uberconf/init-cfg {:ini ini :schema config-schema}))
