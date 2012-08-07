package br.com.caelum.futures

import scala.concurrent.ExecutionContext

object Contexto {
 implicit val ex = ExecutionContext.Implicits.global
}