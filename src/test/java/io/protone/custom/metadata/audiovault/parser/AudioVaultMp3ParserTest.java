package io.protone.custom.metadata.audiovault.parser;

import io.protone.ProtoneApp;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

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
