(ns clj-netty-simple-telnet.core
  (:gen-class)
  (:import [java.net InetSocketAddress]
           [java.util.concurrent Executors]
           [org.jboss.netty.bootstrap ServerBootstrap]
           [org.jboss.netty.channel Channels ChannelPipelineFactory]
           [org.jboss.netty.channel.socket.nio NioServerSocketChannelFactory]
           [org.jboss.netty.handler.codec.string StringEncoder StringDecoder])
   (:use [clojure.tools.logging :as log]
         [clj-netty-simple-telnet.handler :as handler]))

(defn make-channel-factory
  "Returns a Netty channel factory."
  []
  (NioServerSocketChannelFactory.
   (Executors/newCachedThreadPool) (Executors/newCachedThreadPool)))

(defn make-pipeline-factory
  "Returns a Netty pipeline factory."
  []
  (reify ChannelPipelineFactory
    (getPipeline [this]
      (let [pipeline (Channels/pipeline)]
        (.addLast pipeline "server-handler" (handler/make-handler))
        (.addLast pipeline "string-encoder" (StringEncoder.))
        (.addLast pipeline "string-decoder" (StringDecoder.))
        pipeline))))

(defn start-server
  "Start a Netty server."
  [port]
  (let [bootstrap (ServerBootstrap. (make-channel-factory))]
    (doto bootstrap
      (.setPipelineFactory (make-pipeline-factory))
      (.setOption "child.tcpNoDelay" true)
      (.setOption "child.keepAlive" true)
      (.bind (InetSocketAddress. port)))))

;; We start server at port 5000
;; use telnet localhost:5000 to connect this server
(defn main []
  (start-server 5000))
