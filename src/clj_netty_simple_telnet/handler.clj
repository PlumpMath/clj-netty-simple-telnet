(ns clj-netty-simple-telnet.handler
  (:gen-class)
  (:import [org.jboss.netty.channel SimpleChannelUpstreamHandler])
  (:use [clojure.tools.logging :as log]))

(defn channel-connected
  [ctx event]
  (let [channel (.getChannel event)]
    (log/info "Connected:" channel)))

(defn channel-disconnected
  [ctx event]
  (let [channel (.getChannel event)]
    (log/info "Disconnected:" channel)))

(defn message-received
  [ctx event]
  (let [channel (.getChannel event)
        msg (.toString (.getMessage event) "UTF-8")]
    (log/info "Message:" msg "from" channel)
    (.write channel (str "You type: " msg))
    (if (= "quit" (clojure.string/trim msg))
      (.close channel))))

(defn exeception
  [ctx event]
  (let [throwable (.getCause event)]
    (log/error "@exceptionCaught" throwable)
    (-> event .getChannel .close)))

(defn make-handler
  "Returns a Netty handler."
  []
  (proxy [SimpleChannelUpstreamHandler] []
    (channelConnected [ctx event] (channel-connected ctx event))
    (channelDisconnected [ctx event] (channel-disconnected ctx event))
    (messageReceived [ctx event] (message-received ctx event))
    (exceptionCaught [ctx event] (exeception ctx event))))
