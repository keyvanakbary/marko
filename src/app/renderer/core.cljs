(ns app.renderer.core
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [app.renderer.events :as events]
   [app.renderer.routes :as routes]
   [app.renderer.views :as views]
   [app.renderer.config :as config]
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
