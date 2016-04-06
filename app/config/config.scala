package config

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit._
import com.codahale.metrics.graphite.{Graphite, GraphiteReporter}
import com.codahale.metrics.{MetricFilter, SharedMetricRegistries}
import play.api._

object Config extends GlobalSettings {

  override def onStart(app: Application) {
      setupGraphite(app)
      Logger.info("Application has started")

   }


 private def setupGraphite(app: Application)  = {

//  val graphite = new PickledGraphite(new InetSocketAddress("graphite.example.com", 2004));
  val graphite = new Graphite(new InetSocketAddress("localhost", 2003));

  val metricsPrefix = app.configuration.getString("metrics.prefix").getOrElse("NO-PREFIX")

  val registry = SharedMetricRegistries.getOrCreate("default")
  val reporter : GraphiteReporter =  GraphiteReporter.forRegistry(registry)
                                          .prefixedWith(s"$metricsPrefix.${java.net.InetAddress.getLocalHost.getHostName}")
                                          .convertRatesTo(SECONDS)
                                          .convertDurationsTo(MILLISECONDS)
                                          .filter(MetricFilter.ALL)
                                          .build(graphite);
  reporter.start(1, SECONDS);

 }
}
