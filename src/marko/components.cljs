(ns marko.components
  (:require
   [reagent.core :as r]
   ["easymde" :as easymde]))

(def id-counter (atom 0))

(defn- generate-id []
  (swap! id-counter inc)
  (str "editor-" @id-counter))

(defn editor [{:keys [id value options on-change]
               :or {id (generate-id)}}]
  (let [!key-change (atom false)
        !editor (atom nil)
        !value (atom value)
        trigger-change (fn []
                         (let [editor-value (.value @!editor)]
                           (reset! !key-change true)
                           (reset! !value editor-value)
                           (if on-change
                             (on-change editor-value))))]
    (r/create-class
     {:display-name "editor"

      :component-did-mount
      (fn [this]
        (let [all-options
              (-> options
                  (merge {:element (js/document.getElementById id)
                          :initialValue (:value (r/props this))})
                  (clj->js))]
          (reset! !editor (easymde. all-options))))

      :component-did-update
      (fn [this [_ {old-value :value}]]
        (let [props-value (:value (r/props this))]
          (if (or (and (not @!key-change)
                       (not= props-value old-value))
                  (not= props-value @!value))
            (.value @!editor props-value))
          (reset! !key-change false)))

      :reagent-render
      (fn []
        [:div
         {:id (str id "-wrapper")
          :on-key-up trigger-change
          :on-paste trigger-change}
         [:textarea {:id id}]])})))