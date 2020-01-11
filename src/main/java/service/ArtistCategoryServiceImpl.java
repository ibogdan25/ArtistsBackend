package service;

import model.ArtistCategory;
import model.ArtistCategoryPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArtistCategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistCategoryServiceImpl implements ArtistCategoryService{

    private ArtistCategoryRepository artistCategoryRepository;

    @Autowired
    public ArtistCategoryServiceImpl(ArtistCategoryRepository artistCategoryRepository) {
        this.artistCategoryRepository = artistCategoryRepository;
    }

    @Override
    public long add(ArtistCategoryPOJO pojo) {
        ArtistCategory artistCategory = new ArtistCategory();

        artistCategory.setName(pojo.getName());

        return artistCategoryRepository.save(artistCategory).getIdArtistCategory();
    }

    @Override
    public void delete(long id) {

        if(artistCategoryRepository.findById(id).isPresent()){
            artistCategoryRepository.deleteById(id);
            return;
        }

        throw new IllegalArgumentException("There is no ArtistCategory entity with id: "+id+ " in the database");

    }

    @Override
    public void update(ArtistCategoryPOJO pojo) {
        long categoryId = pojo.getIdArtistCategory();
        if (!this.artistCategoryRepository.findById(categoryId).isPresent()) {
            throw new EntityNotFoundException("ArtistCategory with id "+categoryId+" has not been found.");
        }

        ArtistCategory artistCategory = new ArtistCategory();

        //map from pojo to entity
        artistCategory.setIdArtistCategory(categoryId);
        artistCategory.setName(pojo.getName());

        this.artistCategoryRepository.save(artistCategory);


    }

    @Override
    public ArtistCategoryPOJO findById(long id) {

        ArtistCategory artistCategory = this.artistCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ArtistCategory with id "+id+" has not been found."));

        ArtistCategoryPOJO pojo = new ArtistCategoryPOJO();

        //map from entity to pojo
        pojo.setIdArtistCategory(artistCategory.getIdArtistCategory());
        pojo.setName(artistCategory.getName());

        return pojo;
    }

    @Override
    public List<ArtistCategoryPOJO> findAll() {

        List<ArtistCategoryPOJO> pojoList = new ArrayList<>();

        this.artistCategoryRepository.findAll().forEach(x->{
            ArtistCategoryPOJO pojo = new ArtistCategoryPOJO();
            pojo.setIdArtistCategory(x.getIdArtistCategory());
            pojo.setName(x.getName());

            pojoList.add(pojo);
        });
        return pojoList;


    }
}
