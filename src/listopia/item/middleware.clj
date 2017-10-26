(ns listopia.item.middleware)


;; example request modification
(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "server"] "listopia")))
