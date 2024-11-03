package org.movie.manager.adapters.Mapper;

import org.movie.manager.domain.Metadata.Metadata;

import java.util.Arrays;

public class MetadataMapper {
    public static enum Header {
        METADATAID,
        OWNERSHIP,
        NAMEORMEDIUM,
        DESCRIPTION,
        IMBDID,
        IMDBRATING,
        METASCORE,
        RATING,
        MOVIE;

        private Header() {
        }
    }

    public String[] mapData(Metadata medadataData) {
        String[] atts = new String[Header.values().length];
        atts[Header.METADATAID.ordinal()] = String.valueOf(medadataData.getMetadataID());
        if(medadataData.getAvailability() != null){
            atts[Header.OWNERSHIP.ordinal()] = medadataData.getAvailability().getOwnership().toString();
            atts[Header.NAMEORMEDIUM.ordinal()] = medadataData.getAvailability().getNameOrMedium();
            atts[Header.DESCRIPTION.ordinal()] = medadataData.getAvailability().getDescription();
        }else{
            atts[Header.OWNERSHIP.ordinal()] = null;
            atts[Header.NAMEORMEDIUM.ordinal()] = null;
            atts[Header.DESCRIPTION.ordinal()] = null;
        }
        if(medadataData.getImbDdata() != null){
            atts[Header.IMBDID.ordinal()] = medadataData.getImbDdata().getiMDBID();
            atts[Header.IMDBRATING.ordinal()] = String.valueOf(medadataData.getImbDdata().getiMDbRating());
            atts[Header.METASCORE.ordinal()] = String.valueOf(medadataData.getImbDdata().getMetascore());
        }else{
            atts[Header.IMBDID.ordinal()] = null;
            atts[Header.IMDBRATING.ordinal()] = null;
            atts[Header.METASCORE.ordinal()] = null;
        }
        atts[Header.RATING.ordinal()] = String.valueOf(medadataData.getOwnRating().getRating());
        atts[Header.MOVIE.ordinal()] = String.valueOf(medadataData.getMovie().getMovieID());
        return atts;
    }
    public static String[] getHeader() {
        return Arrays.stream(Header.values()).map(Enum::name).toArray(String[]::new);
    }
}
