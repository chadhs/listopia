(ns listopia.util.core)


(defn http-request-mock
  "creates a request defaulting to http"
  [& {:keys [scheme server-port uri request-method params route-params]
      :or {scheme :http server-port 80 request-method :get}}]
  (let [base-mock {:protocol "HTTP/1.1"
                   :scheme scheme
                   :server-port server-port
                   :server-name "localhost"
                   :remote-addr "localhost"
                   :headers {"host" "localhost"}
                   :uri uri
                   :request-method request-method}]
    (cond-> base-mock
      params (assoc :params params)
      route-params (assoc :route-params route-params))))


(defn uuid->str
  "return the plain string value of a given uuid."
  [uuid]
  (str (uuid :id)))


(defn hugsqluuid->javauuid
  "return the java.util.UUID/fromString uuid format from a hugsql uuid map format."
  [uuid]
  (uuid :id))
