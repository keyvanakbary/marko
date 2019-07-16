(ns marko.routes
  (:require
   [secretary.core :as secretary :refer-macros [defroute]]
   [goog.events :as gevents]
   [re-frame.core :as rf]
   [marko.events :as events])
  (:import
   [goog.history Html5History]
   [goog.history EventType])
  )

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true))
  )

(defn app-routes []
  (secretary/set-config! :prefix "#")
  
  (defroute "/" []
    (rf/dispatch [::events/set-active-panel :editor-panel]))

  (defroute "/preferences" []
    (rf/dispatch [::events/set-active-panel :preferences-panel]))

  (hook-browser-navigation!))