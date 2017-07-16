package io.protone.language.pql.impl.visitor.library;

import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.language.pql.impl.visitor.ProtoneQueryLangageBaseVisitorImpl;
import io.protone.library.domain.*;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageLibraryEntityVisitorImpl extends ProtoneQueryLangageBaseVisitorImpl {
    public ProtoneQueryLanguageLibraryEntityVisitorImpl() {
        pqlEntityMap.put("Library", LibLibrary.class);
        pqlEntityMap.put("MediaItem", LibMediaItem.class);
        pqlEntityMap.put("Album", LibAlbum.class);
        pqlEntityMap.put("Artist", LibArtist.class);
        pqlEntityMap.put("Label", LibLabel.class);
        pqlEntityMap.put("Track", LibTrack.class);

    }


    @Override
    public String visitLibrary_entity(ProtoneQueryLanguageParser.Library_entityContext ctx) {

        if (pqlEntityMap.get(ctx.getText().trim()).getSimpleName() == null) {
            return null;
        }
        aliasVariable = ctx.getText().toLowerCase().toCharArray()[0];
        return "SELECT " + aliasVariable + " FROM " + pqlEntityMap.get(ctx.getText()).getSimpleName().trim();
    }


}
