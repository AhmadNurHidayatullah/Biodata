/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Biodata.Biodata;

import Biodata.Biodata.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Acer
 */
public class DatadiriJpaController implements Serializable {

    public DatadiriJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biodata_Biodata_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DatadiriJpaController() {
    }

    public void create(Datadiri datadiri) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(datadiri);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Datadiri datadiri) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            datadiri = em.merge(datadiri);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datadiri.getId();
                if (findDatadiri(id) == null) {
                    throw new NonexistentEntityException("The datadiri with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Datadiri datadiri;
            try {
                datadiri = em.getReference(Datadiri.class, id);
                datadiri.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datadiri with id " + id + " no longer exists.", enfe);
            }
            em.remove(datadiri);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Datadiri> findDatadiriEntities() {
        return findDatadiriEntities(true, -1, -1);
    }

    public List<Datadiri> findDatadiriEntities(int maxResults, int firstResult) {
        return findDatadiriEntities(false, maxResults, firstResult);
    }

    private List<Datadiri> findDatadiriEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Datadiri.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Datadiri findDatadiri(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Datadiri.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatadiriCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Datadiri> rt = cq.from(Datadiri.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
