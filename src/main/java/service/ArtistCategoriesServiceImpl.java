package service;

import model.ArtistCategory;
import model.ArtistCategoryPOJO;
import model.ArtistSubcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ArtistCategoryRepository;
import repository.ArtistSubcategoryRepository;

import java.util.*;

@Service
public class ArtistCategoriesServiceImpl {
    private final ArtistCategoryRepository artistCategoryRepository;
    private final ArtistSubcategoryRepository artistSubcategoryRepository;

    @Autowired
    public ArtistCategoriesServiceImpl(ArtistCategoryRepository artistCategoryRepository, ArtistSubcategoryRepository artistSubcategoryRepository) {
        this.artistCategoryRepository = artistCategoryRepository;
        this.artistSubcategoryRepository = artistSubcategoryRepository;
    }

    public void addCategoryIfNotExists(final ArtistCategory artistCategory) {
        Optional<ArtistCategory> optional = artistCategoryRepository.findFirstByName(artistCategory.getName());
        if (!optional.isPresent()) {
            artistCategoryRepository.save(artistCategory);
        } else {
            final ArtistCategory category = optional.get();
            for(ArtistSubcategory artistSubcategory: artistCategory.getArtistSubcategorySet()) {
                if (!category.getArtistSubcategorySet().stream().anyMatch( sc -> sc.getName().equals(artistCategory.getName()))) {
                    category.getArtistSubcategorySet().add(artistSubcategory);
                    artistSubcategoryRepository.save(artistSubcategory);
                }
            }
            artistCategoryRepository.save(category);
        }
    }

    public List<ArtistCategory> findAll() {

        List<ArtistCategory> pojoList = new ArrayList<>();

        this.artistCategoryRepository.findAll().forEach(x->{
            ArtistCategory artistCategory = new ArtistCategory();
            artistCategory.setIdArtistCategory(x.getIdArtistCategory());
            artistCategory.setName(x.getName());
            artistCategory.setArtistSubcategorySet(x.getArtistSubcategorySet());

            pojoList.add(artistCategory);
        });
        return pojoList;


    }

    public Set<ArtistSubcategory> findAllSubcategories(String categoryName){

        Optional<ArtistCategory> artistCategory = artistCategoryRepository.findFirstByName(categoryName);
        return artistCategory.map(ArtistCategory::getArtistSubcategorySet).orElse(null);
    }
}
