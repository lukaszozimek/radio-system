package io.protone.language.pql.impl.visitor.library;

import com.google.common.base.Strings;
import io.protone.crm.domain.*;
import io.protone.language.pql.ProtoneQueryLanguageBaseVisitor;
import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.library.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageLibraryEntityVisitorImpl extends ProtoneQueryLanguageBaseVisitor<String> {
    private static final String SPACE = " ";
    private Map<String, String> pqlEntityMap = new HashMap<>();

    public ProtoneQueryLanguageLibraryEntityVisitorImpl() {
        pqlEntityMap.put("Library", LibLibrary.class.getSimpleName());
        pqlEntityMap.put("MediaItem", LibMediaItem.class.getSimpleName());
        pqlEntityMap.put("Album", LibAlbum.class.getSimpleName());
        pqlEntityMap.put("Artist", LibArtist.class.getSimpleName());
        pqlEntityMap.put("Label", LibLabel.class.getSimpleName());
        pqlEntityMap.put("Track", LibTrack.class.getSimpleName());

    }


    @Override
    public String visitLibrary_entity(ProtoneQueryLanguageParser.Library_entityContext ctx) {

        if (Strings.isNullOrEmpty(pqlEntityMap.get(ctx.getText().trim()))) {
            return null;
        }
        return "SELECT * FROM " + pqlEntityMap.get(ctx.getText().trim());
    }


    @Override
    protected String aggregateResult(String aggregate, String nextResult) {

        if (Strings.isNullOrEmpty(nextResult)) {
            return aggregate;
        }

        if (Strings.isNullOrEmpty(aggregate) && !Strings.isNullOrEmpty(nextResult)) {
            aggregate = "";
            return aggregate.concat(nextResult);
        }

        if (Strings.isNullOrEmpty(nextResult)) {
            return aggregate;
        }

        return aggregate.concat(SPACE).concat(nextResult);
    }


}
