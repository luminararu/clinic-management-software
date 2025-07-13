package cabinetmedical;

import java.util.List;

/**
 * Interfață generică pentru serviciile CRUD
 * @param <T> Tipul entității
 * @param <ID> Tipul ID-ului entității
 */
public interface CrudService<T, ID> {

    /**
     * Creează o nouă entitate
     * @param entity Entitatea de creat
     * @return Entitatea creată cu ID-ul generat
     */
    T create(T entity);

    /**
     * Găsește o entitate după ID
     * @param id ID-ul entității
     * @return Entitatea găsită sau null
     */
    T findById(ID id);

    /**
     * Găsește toate entitățile
     * @return Lista cu toate entitățile
     */
    List<T> findAll();

    /**
     * Actualizează o entitate existentă
     * @param entity Entitatea de actualizat
     * @return Entitatea actualizată
     */
    T update(T entity);

    /**
     * Șterge o entitate după ID
     * @param id ID-ul entității de șters
     * @return true dacă ștergerea a reușit, false altfel
     */
    boolean delete(ID id);
}