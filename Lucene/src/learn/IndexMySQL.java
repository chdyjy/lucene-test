package learn;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import IkAnalyzer5x.IKAnalyzer5x;
import mysql.M;

public class IndexMySQL {
	public static void main(String[] args) {
		String indexPath = "index";
		boolean create = true;

		try {
			Date start = new Date();
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new IKAnalyzer5x(true);
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

			if (create) {
				// 在索引目录中删除之前的索引文件，重新加了你新的索引
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				// 若存在索引，则增加，否则创建新的索引
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			IndexWriter writer = new IndexWriter(dir, iwc);
			M model = new M();
			ResultSet rs = model.testQuery();
			while(rs.next()){
				Document doc = new Document();

			    doc.add(new IntField("id", rs.getInt("file_list_id"), Field.Store.YES));
			    doc.add(new TextField("title", rs.getString("member_file_name"), Field.Store.YES));
			    doc.add(new StringField("path", rs.getString("file_name"), Field.Store.YES));
			    doc.add(new TextField("content", rs.getString("file_content"), Field.Store.NO));
			    
			    if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
			        System.out.println("[adding]"+rs.getString("member_file_name"));
			        writer.addDocument(doc);
			      } else {
			        System.out.println("haven't down");
			      }
			    
			}
			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");
			writer.close();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
