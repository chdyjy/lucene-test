Lucene test
===========

## Package org.apache.lucene.analysis Description
我对官方文档的简要翻译，不是很严格，也可能有自己的理解，请勿做正式参考
##Parsing（语法分析）？Tokenization（标记化）？Analysis（分析）！
lucene是只接受纯文本输入的一个索引和检索库。它本身并不关心检索的文档的格式，只需要在建立索引前将其转换为纯文本即可。Lucene包含“pre-tokenization”和“post-tokenization”两种功能。

*Tokenization（标记化）*:纯文本向 Lucene索引转换的过程称，也可以理解为将输入文本拆分成小的索引元素(tokens)的过程。 
 
*Pre-tokenization*:包含单并不限于去除HTML标记、  

*Post-tokenization*:包含并不限于以下步骤
* 提取词干：用词干替换，比如“bike”用来替换“bikes”，但是对“bike”的查询会同时包含“bike”和“bikes”的结果  
* 停用词过滤：a、an、the等对于搜索结果来说名没有什么意义，于是我们去掉好了
* 文本标准化：去掉乱七八糟的标记字符什么的就更好了  
* 同义词扩展：对同义词做相同的标记，这样用户搜索的时候就能更匹配到这个同义词集合了，这样能更好一点   
 
## Core Analysis 
Analysis包提供了一种转换机制，可以用这种转换机制将String或者Reader转换为可以被Lucene建立索引的标记。
包中主要的四个类为：
* Analyzer
* CharFilter
* Tokenizer
* TokenFilter

**简单写两句readme占个坑**





