package config

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit._
import com.codahale.metrics.graphite.{Graphite, GraphiteReporter}
import com.codahale.metrics.{MetricFilter, SharedMetricRegistries}
import play.api._

object Config extends GlobalSettings {

  override def onStart(app: Application) {
      val metricsEnabled = app.configuration.getString("metrics.enabled").getOrElse("false").toBoolean
      if (metricsEnabled) {
        setupGraphite(app)
      }
      Logger.info("Application has started")

   }


 private def setupGraphite(app: Application)  = {

//  val graphite = new PickledGraphite(new InetSocketAddress("graphite.example.com", 2004));


  val metricsPrefix = app.configuration.getString("metrics.prefix").getOrElse("NO-PREFIX")
  val metricsHost = app.configuration.getString("metrics.server.address").getOrElse("localhost")
  val metricsPort = app.configuration.getInt("metrics.server.port").getOrElse(2003)

  val graphite = new Graphite(new InetSocketAddress(metricsHost, metricsPort));

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
