package lucene_project;


import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.index.IndexWriter;
import lucene_project.IndexCreator;
import lucene_project.SearchQuery;


public class Lucene_Main {
	

	
	IndexWriter writer;
	public static void main(String args[]) throws IOException 
	{
		if(args.length!=1)
		{
			System.out.println("Please pass the Document Folder path as argument");
		}
		else
		{
		IndexCreator indexCreate=new IndexCreator("index", args[0]);
		indexCreate.createIndex();
		
		System.out.println("Please enter the Query String:");
		Scanner in = new Scanner(System.in); 
		  
        String queryString = in.nextLine();
        in.close();
		SearchQuery search=new SearchQuery("index",queryString );
		try {
			search.searchIndex();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
}


