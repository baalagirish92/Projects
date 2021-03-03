package lucene_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.jsoup.Jsoup;

public class Parsers {
	public static Document parseHTML(Path p) throws IOException
	{  	Document doc= new Document();
		org.jsoup.nodes.Document htmlDoc = Jsoup.parse(new File(p.toString()),"UTF-8");
		if (htmlDoc.title() == null || htmlDoc.title().isEmpty()) {
			
			doc.add(new StringField("path",p.toString(), Field.Store.YES));
			doc.add(new TextField("title", p.getFileName().toString().toLowerCase(), Field.Store.YES));
			doc.add(new TextField("body", htmlDoc.body().text(), Field.Store.YES));
			doc.add(new TextField("summary", htmlDoc.getElementsByTag("summary").text(), Field.Store.YES));
			doc.add(new TextField("date", htmlDoc.getElementsByTag("date").text(), Field.Store.YES));
			doc.add(new TextField("last_Mod_time",Files.getLastModifiedTime(p).toString(),Field.Store.YES));
		} 
		else {
			doc.add(new StringField("path",p.toString(), Field.Store.YES));
			doc.add(new TextField("title", htmlDoc.title(), Field.Store.YES));
			doc.add(new TextField("body", htmlDoc.body().text(), Field.Store.YES));
			doc.add(new TextField("summary", htmlDoc.getElementsByTag("summary").text(), Field.Store.YES));
			doc.add(new TextField("date", htmlDoc.getElementsByTag("date").text(), Field.Store.YES));
			doc.add(new TextField("last_Mod_time",Files.getLastModifiedTime(p).toString(),Field.Store.YES));
			
		}
	return doc;
	}
	public static Document parseTXT(Path p) throws IOException
	{  	Document doc= new Document();
	File file = new File(p.toString()); 
	  
	  BufferedReader br = new BufferedReader(new FileReader(file)); 
	  StringBuilder stringBuilder = new StringBuilder();
	  String st; 
	  while ((st = br.readLine()) != null) 
		  stringBuilder.append(st).append("\n");
	  	doc.add(new StringField("path",p.toString(), Field.Store.YES));
	  	doc.add(new TextField("title", p.getFileName().toString().toLowerCase(), Field.Store.YES));
		doc.add(new TextField("body", stringBuilder.toString(), Field.Store.YES));
		doc.add(new TextField("last_Mod_time",Files.getLastModifiedTime(p).toString(),Field.Store.YES));

		br.close();
		return doc;
		
	}

}
