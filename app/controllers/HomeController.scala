package controllers

import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

object HomeController extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {


    //var obj = com.kenshoo.play.metrics.MetricsController.metrics
    Ok(views.html.index("Your new application is ready."))
  }

}
