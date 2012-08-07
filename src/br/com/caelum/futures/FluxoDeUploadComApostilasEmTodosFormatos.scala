package br.com.caelum.futures
import scala.concurrent._
import scala.concurrent.util.Duration
import scala.concurrent.util.duration._
import java.util.concurrent.TimeUnit._
import java.io.File
import Contexto._

object FluxoDeUploadComApostilaEmTodosFormatos extends App {
  val geraApostilas = future {
    val arquivos = List("FJ-31", "FJ-26", "FJ-91", "FJ-25", "FJ-21", "FJ-16", "FJ-11")
    arquivos.map(Apostila.gera)
  }
  //criando uma especie de expectativa a ser atendida no meio do
  //processamento assincrono
  val envioDePDFParaAmazonConcluido = promise[Seq[String]]
  
  val sobeParaAmazon = geraApostilas.map { apostilas =>
    val urls = Amazon.uploadPDF(apostilas)
    envioDePDFParaAmazonConcluido.success(urls)
    //mobi e epub
    Amazon.uploadOutrosFormatos(apostilas)
    urls
  }
  
  val enviaEmail = future {
    //poderia enviar um email avisando que já começou a subir os pdfs
    envioDePDFParaAmazonConcluido.future.onSuccess {
      case urls => Mailer.send("alberto.souza@caelum.com.br",urls)
    }
  }

  println("ja avisei para os usuarios que tá gerando")
  while(true){}
}