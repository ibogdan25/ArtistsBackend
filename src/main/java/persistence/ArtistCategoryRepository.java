package persistence;

import model.ArtistCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ArtistCategoryRepository implements IArtistCategoryRepository {

    static SessionFactory sessionFactory;

    public ArtistCategoryRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(ArtistCategory entity) throws Exception {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            }catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Integer integer) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                ArtistCategory artistCategory = session.createQuery("from ArtistCategory where idArtistCategory=:key",ArtistCategory.class)
                        .setParameter("key",integer)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(artistCategory);
                tx.commit();
            }catch (RuntimeException ex){
                if(tx!=null){
                    tx.rollback();
                }
            }
        }

    }

    @Override
    public void update(ArtistCategory entity) {
        try(Session session=sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx=session.beginTransaction();
                ArtistCategory category = session.load(ArtistCategory.class,new Integer(entity.getIdArtistCategory()));
                category.setName(entity.getName());
                category.setDescription(entity.getDescription());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Iterable<ArtistCategory> findAll() {
        try(Session session=sessionFactory.openSession()){
            Transaction  tx=null;
            try{
                tx=session.beginTransaction();
                List<ArtistCategory> categories=session.createQuery("from ArtistCategory ",ArtistCategory.class).list();

                tx.commit();
                return categories;

            }catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public ArtistCategory findOne(Integer integer) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                ArtistCategory category=session.createQuery("from ArtistCategory where id=:key",ArtistCategory.class)
                        .setParameter("key",integer)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return category;
            }catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }

        }
        return null;
    }
}
