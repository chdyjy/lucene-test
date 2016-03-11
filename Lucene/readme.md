Lucene test
===========
## Lucene文件结构
### 层次结构
* index：一个索引存放在一个目录中；
* segment：一个索引中可以有多个段，段与段之间是独立的，添加新的文档可能产生新段，不同的段可以合并成一个新段；
* document：文档是创建索引的基本单位，不同的文档保存在不同的段中，一个段可以包含多个文档；
* field:域，一个文档包含不同类型的信息，可以拆分开索引；
* term：词，索引的最小单位，是经过词法分析和语言处理后的数据。
### 正向信息
按照层次依次保存了从索引到词的包含关系：index-->segment-->document-->field-->term。
### 反向信息
反向信息保存了词典的倒排表映射：term-->document。

## Package org.apache.lucene.analysis Description
我对官方文档的简要翻译，不是很严格，也可能有自己的理解，请勿做正式参考
###Parsing（语法分析）？Tokenization（标记化）？Analysis（分词）！
lucene是只接受纯文本输入的一个索引和检索库。它本身并不关心检索的文档的格式，只需要在建立索引前将其转换为纯文本即可。Lucene包含“pre-tokenization”和“post-tokenization”两种功能。

*Tokenization（标记化）*:纯文本向 Lucene索引转换的过程称，也可以理解为将输入文本拆分成小的索引元素(tokens)的过程。 
 
*Pre-tokenization*:包含单并不限于去除HTML标记、  

*Post-tokenization*:包含并不限于以下步骤

* 提取词干：用词干替换，比如“bike”用来替换“bikes”，但是对“bike”的查询会同时包含“bike”和“bikes”的结果  
* 停用词过滤：a、an、the等对于搜索结果来说名没有什么意义，于是我们去掉好了
* 文本标准化：去掉乱七八糟的标记字符什么的就更好了  
* 同义词扩展：对同义词做相同的标记，这样用户搜索的时候就能更匹配到这个同义词集合了，这样能更好一点  

### Core Analysis 
核心分词包提供了一种转换机制，可以用这种转换机制将String或者Reader转换为可以被Lucene建立索引的标记。
包中主要的四个类为：Analyzer, CharFilter, Tokenizer, TokenFilter。

其中要注意的是：Analyzer可以认为是分词器的工厂，它并不直接处理文本，而是负责构造CharFilter、Tokenizer、还有TokenFilter这些类去处理文本。CharFilter是Reader的子类用于偏移量的追踪，Tokenizer只负责将输入文本转换为tokens，它属于TokenStream，TokenFilter修改了tokens及其内容的流。

## Package org.apache.lucene.search Description

### Search Basic
我们调用IndexSearcher.search(Query,int)来进行检。一旦一个Query提交给IndexSearcher，评分机制也开始运行。

### Query Classes
**TermQuery**：一个构造TermQuery的简单例子
```TermQuery tq = new TermQuery(new Term("fieldName", "term"));```
表示在所有文档中匹配域名为“fieldName”且包含词元“term”的查询。  

**BooleanQuery**：BooleanQuery 包含了多个 BooleanClause，每个clause（从句）包含一个子查询（查询实例）和一个描述这个子查询如何与其他clause（从句）合并的操作符：

* SHOULD - 含有此操作符的从句可以在结果集中产生，但并不必须。如果一个查询（query）全部由SHOULD从句构成，那么产生的结果集中的每个文档（document）最少匹配所有SHOULD从句的一个从句。
* MUST - 必须出现
* MUST NOT - 必须不出现

**Phrases**：

* PhraseQuery（短语查询）- 匹配词序列。PhraseQuery uses a slop factor to determine how many positions may occur between any two terms in the phrase and still be considered a match. The slop is 0 by default, meaning the phrase must match exactly.

* MultiPhraseQuery — A more general form of PhraseQuery that accepts multiple Terms for a position in the phrase. For example, this can be used to perform phrase queries that also incorporate synonyms.

* SpanNearQuery — Matches a sequence of other SpanQuery instances. SpanNearQuery allows for much more complicated phrase queries since it is constructed from other SpanQuery instances, instead of only TermQuery instances.

**TermRangeQuery**：词元范围查询，如在文档中查询以字母a到c开头的词。

**NumericRangeQuery**：数值范围查询。

**PrefixQuery, WildcardQuery, RegexpQuery**：前缀查询，通配符查询（“*”、“？”等）,正则表达式查询。

**FuzzyQuery**：相似词语查询。

### Scoring — Introduction
Lucene 评分机制适用于多个信息检索模型，包括：

* 空间向量模型（VSM）
* 概率模型如“Okapi”、“DFR”等
* 语言模型 

### Scoring — Basics

通常来说，查询决定了哪个文档是匹配的（一个二进制的决定），而相似度决定了怎样给匹配到的文档评分。

### Changing Scoring — Similarity
### Appendix: Search Algorithm






**简单写两句readme占个坑**





