package io.protone.service.metadata.audiovault.parser;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lukaszozimek on 16/03/2017.
 */

public class AudioVaultMp3ParserTest {

    //@Test
    public void parse() throws TikaException, SAXException, IOException {
        Parser parser = new AutoDetectParser();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("audio/audiovault/M-3517.MP3");
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();

        parser.parse(inputStream, handler, metadata, pcontext);

        System.out.print("test");
    }
}
