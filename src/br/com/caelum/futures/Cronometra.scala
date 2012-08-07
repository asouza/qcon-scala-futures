package br.com.caelum.futures

object Cronometra {

  def apply[T](corpo: => T) = {
    val t1 = System.currentTimeMillis
    	val retorno = corpo
    	val tempoGasto = (System.currentTimeMillis - t1)/1000
    	println(s"O tempo gasto foi de aproximadamente $tempoGasto segundos")
    retorno
  }
}