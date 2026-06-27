package loggerf.logger

import sbt.util.Logger

abstract class SbtLoggerBase extends CanLog {

  def logger: Logger

  override def debug(message: => String): Unit = logger.debug(message)

  override def debug(throwable: Throwable)(message: => String): Unit = {
    logger.debug(message)
    logger.trace(throwable)
  }

  override def info(message: => String): Unit = logger.info(message)

  override def info(throwable: Throwable)(message: => String): Unit = {
    logger.info(message)
    logger.trace(throwable)
  }

  override def warn(message: => String): Unit = logger.warn(message)

  override def warn(throwable: Throwable)(message: => String): Unit = {
    logger.warn(message)
    logger.trace(throwable)
  }

  override def error(message: => String): Unit = logger.error(message)

  override def error(throwable: Throwable)(message: => String): Unit = {
    logger.error(message)
    logger.trace(throwable)
  }
}
