package com.recall;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Field;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteIndex {

    private static Document createDocument(Integer id, String title)
    {
        Document document = new Document();
        document.add(new StringField("id", id.toString() , Field.Store.YES));
        document.add(new TextField("title", title , Field.Store.YES));
        return document;
    }

    private static IndexWriter createWriter(Analyzer analyzer) throws IOException
    {
        FSDirectory dir = FSDirectory.open(Paths.get(indexPath.INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        return new IndexWriter(dir, config);
    }

    public static void main(String[] args) throws Exception
    {
        IndexWriter writer = createWriter(new SmartChineseAnalyzer());
        List<Document> documents = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("target/classes/data.txt"));
            String str;
            int i = 1;
            while ((str = in.readLine()) != null && i < 20000000) {
                Document document = createDocument(i, str);
                documents.add(document);
                i += 1;
                if (i % 10000 == 0) {
                    System.out.println("read: " + i);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("write to index...");
        //Let's clean everything first
        writer.deleteAll();
        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }

}
