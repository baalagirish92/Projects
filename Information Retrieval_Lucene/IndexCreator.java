package lucene_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lucene_project.Parsers;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class IndexCreator {

	String dataPath;
	String indexPath;
	public IndexCreator(String indexFolder, String dataFolder) {
		
		dataPath=dataFolder;
		
		indexPath=indexFolder;
	}
	void createIndex() throws IOException
	{
		IndexWriter writer=new IndexWriter(FSDirectory.open(Paths.get(indexPath)),new IndexWriterConfig(new EnglishAnalyzer()));
		writer.deleteAll();
        writer.commit();
		
		Files.walk(Paths.get(dataPath)).filter(Files:: isRegularFile).forEach(filePath -> {
		        if (filePath.getFileName().toString().toLowerCase().endsWith(".html")||filePath.getFileName().toString().toLowerCase().endsWith(".htm"))
					try {
						writer.addDocument(Parsers.parseHTML(filePath));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else if (filePath.getFileName().toString().toLowerCase().endsWith(".txt"))
					try {
						writer.addDocument(Parsers.parseTXT(filePath));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        System.out.println("Parsed and Indexed: "+filePath);
		    });
		writer.close();
	}
}
