/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.example.http.websocketx.autobahn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioEventLoop;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * A Web Socket echo server for running the <a href="http://www.tavendo.de/autobahn/testsuite.html">autobahn</a> test
 * suite
 */
public class AutobahnServer {

    private final int port;

    public AutobahnServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.eventLoop(new NioEventLoop(), new NioEventLoop())
             .channel(new NioServerSocketChannel())
             .localAddress(port)
             .childHandler(new AutobahnServerInitializer());

            ChannelFuture f = b.bind().sync();
            System.out.println("Web Socket Server started at port " + port);
            f.channel().closeFuture().sync();

        } finally {
            b.shutdown();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 9000;
        }
        new AutobahnServer(port).run();
    }
}
