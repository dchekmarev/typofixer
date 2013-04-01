package ru.hh.typofixer

import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import java.util.concurrent.Executors
import org.jboss.netty.channel._
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.buffer.ChannelBuffers
import java.net.InetSocketAddress
import scala.collection.JavaConversions._

object Bootstrap {
  def main(args: Array[String]) {
    val morpher = Morpher.configured
    val nThreads = Runtime.getRuntime.availableProcessors
    val b = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newSingleThreadExecutor, Executors.newFixedThreadPool(nThreads), nThreads))
    b.setPipelineFactory(new ChannelPipelineFactory {
      def getPipeline: ChannelPipeline = {
        val pipeline = Channels.pipeline
        pipeline.addLast("decoder", new HttpRequestDecoder)
        pipeline.addLast("encoder", new HttpResponseEncoder)
        pipeline.addLast("handler", new SimpleChannelUpstreamHandler {
          override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
            val response = new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK)
            response.setHeader("Content-Type", "text/html; charset=UTF-8")
            val buf = ChannelBuffers.dynamicBuffer
            val parameters = new QueryStringDecoder((e.getMessage.asInstanceOf[HttpRequest]).getUri).getParameters
            val texts = parameters.get("text")
            val word = new Word(if (texts == null) "" else texts.mkString(" "))
            val result = morpher.apply(word)
            buf.writeBytes("<form><input name=text><input type=submit></form><br/><br/>".getBytes)
            buf.writeBytes("result = ".getBytes)
            buf.writeBytes(result.value(Dictionary.prod).getBytes)
            buf.writeBytes("<br/><br/>".getBytes)
            buf.writeBytes(result.toString.getBytes)
            response.setContent(buf)
            e.getChannel.write(response).addListener(ChannelFutureListener.CLOSE)
          }
        })
        pipeline
      }
    })
    b.bind(new InetSocketAddress(8080))
  }
}
