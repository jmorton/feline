(ns feline.app
  "Define handlers and middleware"
  (:require [compojure.core :refer :all]
            [ring.middleware.accept :as accept]
            [ring.middleware.keyword-params :as keyword-params]
            [ring.middleware.params :as params]
            [ring.middleware.problem :as problem]
            [feline.middleware :as middleware]
            [feline.cat :refer [cat-resource]]
            [feline.problem :refer [problem-resource problem-transform]]))

(defn new-handler
  "Build a middleware wrapped handler for app. This approach makes
  dependencies (components) available to handling functions."
  [db msg]
  (context "/" req
    (-> (routes (cat-resource db msg)
                (problem-resource db msg))
        (accept/wrap-accept)
        (middleware/wrap-content-type)
        (middleware/wrap-authorize)
        (middleware/wrap-authenticate)
        (keyword-params/wrap-keyword-params)
        (params/wrap-params)
        (problem/wrap-problem (problem-transform req db msg)))))
