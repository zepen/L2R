package recall;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ReadIndex
{

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(indexPath.INDEX_DIR));
        IndexReader reader = DirectoryReader.open(dir);
        return new IndexSearcher(reader);
    }

    private static TopDocs searchByTitle(IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("title", new SmartChineseAnalyzer());
        Query firstNameQuery = qp.parse("土豆");
        return searcher.search(firstNameQuery, 100);
    }

    public static void main(String[] args) throws Exception
    {
        IndexSearcher searcher = createSearcher();
        TopDocs foundDocs = searchByTitle(searcher);

        System.out.println("Total Results :: " + foundDocs.totalHits);

        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(d.get("title"));
        }
    }

}