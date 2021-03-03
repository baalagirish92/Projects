package lucene_project;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.BM25Similarity;

 class SearchQuery {

	
	String indexPath;
	String queryString;
	
	public SearchQuery(String index,String searchterm)
	{
		indexPath=index;
		queryString=searchterm;
	}
	
	 void searchIndex() throws IOException, ParseException
	{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
	    IndexSearcher searcher = new IndexSearcher(reader);
	    searcher.setSimilarity(new BM25Similarity());
	    Analyzer analyzer = new EnglishAnalyzer();
	    
	    MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] {"title","body","summary","date"}, analyzer);
	    Query query = parser.parse(queryString);
	    TopDocs matches = searcher.search(query, 10);
		Document doc;
			int rank = 1;
			System.out.println("================================================================================\n");
			System.out.println("SEARCH RESULTS:\n");
			if (matches.scoreDocs.length > 0) 
			{
				for (ScoreDoc scoreDoc : matches.scoreDocs) 
				{
					doc = searcher.doc(scoreDoc.doc);
				
					if(doc.get("path").endsWith(".txt"))
					{
					System.out.println("Rank:"+ rank++ +"\t\t"+"Relevance Score :"+scoreDoc.score+"\n"+"File Name/Title:"+doc.get("title")+"\n"+"Path:"+doc.get("path")+"\t\t"+"Last_Mod_time:"+doc.get("last_Mod_time")+"\n");
					}
					else
					{
					System.out.println("Rank:"+ rank++ +"\t\t"+"Relevance Score :"+scoreDoc.score+"\n"+"File Name/Title:"+doc.get("title")+"\n"+"Path:"+doc.get("path")+"\t\t"+"Last_Mod_time:"+doc.get("last_Mod_time"));
					if(!doc.get("summary").isEmpty())
					
						System.out.println("Summary:"+doc.get("summary")+"\n");
					else 
						System.out.println();
					
					}
						
				}
					
					
			} 
			
				
			
			else 
				System.out.println("No Match Found for given query");
	}
}
