package learn;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class WhiteSpaceAnalyzer extends Analyzer{

//	private Version matchVersion;
//	
//	public WhiteSpaceAnalyzer(){
//		this.matchVersion = matchVersion;
//	}
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
	     final Tokenizer source = new WhitespaceTokenizer();
	     TokenStream result = new LengthFilter(source, 3, 5);
	     return new TokenStreamComponents(source, result);
		//return new TokenStreamComponents(new WhitespaceTokenizer());
	}
	
	public static void main(String[] args) {
		
		final String text = "This is a Test in WhitespaceAnalyzer";
		Analyzer analyzer = new WhiteSpaceAnalyzer();	
		TokenStream ts = analyzer.tokenStream("field", new StringReader(text));
		
		CharTermAttribute cta = ts.addAttribute(CharTermAttribute.class);
		
		try {
			ts.reset();
			
			while(ts.incrementToken()){
				System.out.println(cta.toString());
			}
			ts.end();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				ts.close();
				analyzer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



}
