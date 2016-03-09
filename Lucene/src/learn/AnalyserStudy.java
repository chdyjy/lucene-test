package learn;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

import IkAnalyzer5x.IKAnalyzer5x;

public class AnalyserStudy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "lucene学习 好好学习,天天向上,锄禾日当午，汗滴禾下土,this "
				+ "is a good person";
		String fieldName = "fieldName";
		String keywords = "学习 汗滴";
		
		//String docsPath = "source";
		String indexPath = "index";
		
		Analyzer analyzer = new IKAnalyzer5x(true);
		
		Directory directory = null;
		IndexWriter iwriter = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;
		try {
			//配置IndexWriterConfig
//			Path docDir = Paths.get(indexPath);
//			directory = FSDirectory.open(docDir);
			
			directory = new RAMDirectory();//Store the index in memory;
			
			//directory = new RAMDirectory();
			IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iwriter = new IndexWriter(directory,iwConfig);
			//索引
			Document doc = new Document();
//			doc.add(new IntField("ID",10000,Field.Store.YES));
			doc.add(new StringField(fieldName,str,Field.Store.YES));
			iwriter.addDocument(doc);
			iwriter.close();
			
			//实例化搜索器
			ireader = DirectoryReader.open(directory);//获取准实时搜索所用的Reader已经改用DirectoryReader.open
			isearcher = new IndexSearcher(ireader);
			
			QueryParser qp = new QueryParser(fieldName,analyzer);
			qp.setDefaultOperator(QueryParser.OR_OPERATOR);
			Query query = qp.parse(keywords);
			
			StringReader stringReader = new StringReader(str);
			TokenStream tokenStream = analyzer.tokenStream("", stringReader);
			tokenStream.reset();
			CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
			System.out.println("分词技术："+analyzer.getClass());
			while(tokenStream.incrementToken()){
				System.out.println(term.toString()+"|");
			}
			
			TopDocs topDocs = isearcher.search(query, 5);
			System.out.println("命中："+topDocs.totalHits);
			
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for(int i=0; i<topDocs.totalHits ; i++){
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("内容："+targetDoc.toString());
			}
			
			
		} catch (CorruptIndexException e) { 
			e.printStackTrace();
		} catch (LockObtainFailedException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		} catch (ParseException e) { 
			e.printStackTrace();
		} finally{
			if(ireader != null){
				try {
					ireader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(directory != null){
				try {
					directory.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}










/*
public static void print(Analyzer analyzer){
	StringReader stringReader = new StringReader(str);	
	try {
		TokenStream tokenStream = analyzer.tokenStream("", stringReader);
		tokenStream.reset();
		CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
		System.out.println("分词技术："+analyzer.getClass());
		while(tokenStream.incrementToken()){
			System.out.println(term.toString()+"|");
		}
		System.out.println();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}*/