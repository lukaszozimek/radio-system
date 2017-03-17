package io.protone.custom.metadata.audiovault.parser;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.CompositeParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 16/03/2017.
 */
public class AudioVaultWaveParserTest {
    @Test
    public void parse() throws Exception {
        Parser parser = new AutoDetectParser();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("audio/audiovault/M-3500.WAV");
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();

//        parser.parse(inputStream, handler, metadata, pcontext);

    }

}
