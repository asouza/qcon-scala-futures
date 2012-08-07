package br.com.caelum.sincrono

import br.com.caelum.futures.Amazon
import br.com.caelum.futures.Apostila
import br.com.caelum.futures.Mailer

object FluxoDeUpload extends App {
  //gera apostilas
  val nomesDeArquivos = List("FJ-31", "FJ-26", "FJ-91", "FJ-25", "FJ-21", "FJ-16", "FJ-11")
  val apostilas = nomesDeArquivos.map(Apostila.gera)
  
  //sobe para amazon
  val urls = Amazon.uploadPDF(apostilas)
  
  //manda email
  Mailer.send("alberto.souza@caelum.com.br",urls)
  
  println("E so agora eu respondo alguma coisa para meu usuario...")
}