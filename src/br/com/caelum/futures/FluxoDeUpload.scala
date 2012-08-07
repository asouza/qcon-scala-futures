package br.com.caelum.futures
import scala.concurrent._
import scala.concurrent.util.Duration
import scala.concurrent.util.duration._
import java.util.concurrent.TimeUnit._
import java.io.File
import Contexto._

object FluxoDeUpload extends App{ 
	
	val geraApostilas:Future[List[Apostila]] = future {
		val arquivos = List("FJ-31","FJ-26","FJ-91","FJ-25","FJ-21","FJ-16","FJ-11")
		val apostilas = arquivos.map(Apostila.gera)
		apostilas
	}
	
	//NAO OLHE DAQUI PARA BAIXO
	
	val sobeParaAmazon = geraApostilas.map(apostilas => Amazon.uploadPDF(apostilas))
	
	val enviaEmail = sobeParaAmazon.map(urls => Mailer.send("alberto.souza@caelum.com.br",urls))

	
	println("ja avisei para os usuarios que tá gerando")
	
	Await.ready(enviaEmail, Duration.Inf)
}