package loggerf.logger

import sbt.util.Logger

final class SbtLogger(override val logger: Logger) extends SbtLoggerBase
object SbtLogger {

  def sbtLoggerCanLog(implicit logger: Logger): CanLog = new SbtLogger(logger)

  @deprecated(message = "Use SbtLogger.sbtLoggerCanLog(sbt.util.Logger) instead", since = "1.2.0")
  def sbtLogger(implicit logger: Logger): CanLog = sbtLoggerCanLog(logger)

}
