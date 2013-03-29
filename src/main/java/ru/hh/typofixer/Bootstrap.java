package ru.hh.typofixer;

import com.google.common.base.Joiner;
import static java.util.Collections.emptyList;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class Bootstrap {
  public static void main(String[] args) {
    final Morph morpher = Morpher.configured();

    final ResponseWriter writer = new ToStringResponseWriter();

    int nThreads = Runtime.getRuntime().availableProcessors();
    ServerBootstrap b = new ServerBootstrap(
        new NioServerSocketChannelFactory(
            Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(nThreads), nThreads)
    );
    b.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("handler", new SimpleChannelUpstreamHandler() {
          @Override
          public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK);
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");
            ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
            Map<String, List<String>> parameters = new QueryStringDecoder(((HttpRequest) e.getMessage()).getUri()).getParameters();
            Word word = new Word(Joiner.on(' ').skipNulls().join(nvl(parameters.get("text"), emptyList())));
            Token result = morpher.apply(word);
            buf.writeBytes(result.toString().getBytes());
            buf.writeBytes("\n".getBytes());
            buf.writeBytes(result.getValue(Dictionary.dict).getBytes());
            response.setContent(buf);
            e.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
          }
        });
        return pipeline;
      }
    });

    b.bind(new InetSocketAddress(8080));
  }

  private static <T> T nvl(T checked, T def) {
    return checked != null ? checked : def;
  }
}
