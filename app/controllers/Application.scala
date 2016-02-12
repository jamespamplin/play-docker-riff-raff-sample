package controllers

import play.api.mvc._
import buildinfo.BuildInfo

class Application extends Controller {

  def index = Action {
    val buildInfo = BuildInfo.toMap.map {
      case (k, v) => s"$k: $v"
    }.mkString("\n")

    Ok(views.html.index(buildInfo))
  }

  def healthcheck = Action {
    Ok("Healthy")
  }

}
