(ns app.renderer.views
  (:require
   [reagent.core :as r]
   [app.renderer.events :as events]
   [app.renderer.components :as components]
   [re-frame.core :as rf]
   [app.renderer.subs :as subs]))

(defn editor-panel []
  (let [content (rf/subscribe [::subs/editor-content])]
    [:div
     [:div
      [components/editor
       {:value @content
        :on-change #(rf/dispatch [::events/set-editor-content %])
        :options {:toolbar false
                  :status false
                  :autofocus true
                  :spellChecker false}}]
      [:pre @content]
      [:button {:on-click #(rf/dispatch [::events/set-editor-content "some content"])} "Reset"]]

     [:div
      [:a {:href "#/preferences"}
       "Preferences"]]]))

(defn preferences-panel []
  [:div
   [:h1 "Preferences"]

   [:div
    [:a {:href "#/"}
     "Finish"]]])

(defn- panels [panel-name]
  (case panel-name
    :editor-panel [editor-panel]
    :preferences-panel [preferences-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (rf/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))