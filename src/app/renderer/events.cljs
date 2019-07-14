(ns app.renderer.events
  (:require
   [re-frame.core :as rf]
   [app.renderer.db :as db]
   ))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(rf/reg-event-db
 ::set-editor-content
 (fn [db [_ content]]
   (assoc db :editor-content content)))
