package br.com.caelum.futures

import java.io.File
import scala.collection.Seq
import scala.collection.mutable.Buffer

class Apostila(val pdf: File, val mobi: File, val epub: File)

object Apostila {
  def gera(caminhoDoArquivo: String): Apostila = {
    println("Gerando apostila " + caminhoDoArquivo)
    Thread.sleep(2000)
    new Apostila(new File(s"${caminhoDoArquivo}.pdf"),
      new File(s"${caminhoDoArquivo}.mobi"),
      new File(s"${caminhoDoArquivo}.epub"))
  }
}

object Amazon {
  def uploadPDF(apostilas: List[Apostila]):Seq[String] = {
    Cronometra {
      apostilas.map(apostila => {
        println("Subindo " + apostila.pdf.getName)
        Thread.sleep(500)
        "http://s3.amazonaws.com/apostilas/" + apostila.pdf.getName
      }).foldLeft(Buffer[String]())((lista,url) => lista.+=(url))
    }
    
    
  }

  def uploadOutrosFormatos(apostilas: List[Apostila]) {
    apostilas.map(apostila => {
      println("Subindo " + apostila.mobi.getName)
      println("Subindo " + apostila.epub.getName)
      Thread.sleep(1500)
    })
  }
}

object Mailer {
  def send(emailDoResponsavel: String, urlsDasApostilas: Seq[String]) {
    val email = s""" Enviando email para $emailDoResponsavel
    		com as novas apostilas buildadas:
    		$urlsDasApostilas
    """
    println(email)
    Thread.sleep(3000)
  }
}