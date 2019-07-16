(ns marko.app
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [marko.events :as events]
   [marko.routes :as routes]
   [marko.views :as views]
   [marko.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (r/render [views/main-panel]
            (.getElementById js/document "app")))

(defn start! []
  (routes/app-routes)
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))

(start!)
