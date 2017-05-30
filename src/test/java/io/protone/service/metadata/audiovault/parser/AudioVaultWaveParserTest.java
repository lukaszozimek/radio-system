package io.protone.service.metadata.audiovault.parser;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.InputStream;

/**
 * Created by lukaszozimek on 16/03/2017.
 */


public class AudioVaultWaveParserTest {

   // @Test
    public void parse() throws Exception {
        Parser parser = new AutoDetectParser();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audiovault/M-3500.WAV");
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(inputStream, handler, metadata, pcontext);

    }

    //@Test
    public void parseMP2() throws Exception {

        //TODO We need more example connected with MP2
        Parser parser = new AutoDetectParser();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audiovault/2WW.mp2");
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(inputStream, handler, metadata, pcontext);

    }
}
