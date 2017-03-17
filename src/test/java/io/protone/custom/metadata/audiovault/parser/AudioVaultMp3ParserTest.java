package io.protone.custom.metadata.audiovault.parser;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultMp3ParserTest {

    @Test
    public void parse() throws TikaException, SAXException, IOException {
        AudioVaultMp3Parser audioVaultMp3Parser = new AudioVaultMp3Parser();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("audio/audiovault/M-3517.MP3");
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();

      // audioVaultMp3Parser.parse(inputStream, handler, metadata, pcontext);
    }
}
