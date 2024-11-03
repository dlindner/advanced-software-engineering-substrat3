package org.movie.manager.adapters.PersistentRepositories;

import org.movie.manager.adapters.Database;
import org.movie.manager.adapters.EntityManager;
import org.movie.manager.adapters.Mapper.MetadataMapper;
import org.movie.manager.domain.Metadata.Metadata;
import org.movie.manager.domain.Metadata.MetadataRepository;

import java.util.*;

public class PersistentMetadataRepository implements MetadataRepository {
    private final EntityManager entityManager;

    private Database csvDB;

    public PersistentMetadataRepository(EntityManager entityManager, Database csvDB) {
        this.entityManager = entityManager;
        this.csvDB = csvDB;
    }

    @Override
    public Collection<Metadata> getAllMetadata() {
        return entityManager.find(Metadata.class);
    }

    @Override
    public Optional<Metadata> getMetadata(UUID metadataID) {
        Metadata metadata = (Metadata)entityManager.find(Metadata.class, metadataID);
        if (metadata == null)
            return null;
        return Optional.of(metadata);
    }

    @Override
    public void update(Metadata metadata) {
        Metadata metadataALt = entityManager.find(metadata.getPrimaryKey());
        if(metadataALt != null){
            entityManager.remove(metadataALt);
        }

        try {
            entityManager.persist(metadata);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Save
        MetadataMapper csvMetadataMapper = new MetadataMapper();
        List<Object[]> csvDataMetadata = new ArrayList<>();
        List<Metadata> alleMetadata = this.entityManager.find(Metadata.class);
        alleMetadata.forEach( e -> csvDataMetadata.add( (csvMetadataMapper.mapData(e) )));
        csvDB.saveData("Metadata.csv", csvDataMetadata, MetadataMapper.getHeader());
    }
}
