(defproject clj-netty-simple-telnet "0.1.0-SNAPSHOT"
  :description "A simple telnet server written in Clojure with netty 3.x"
  :url "https://github.com/coldnew/clj-netty-simple-telnet"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [io.netty/netty "3.6.6.Final"]]

  :main clj-netty-simple-telnet.core)
