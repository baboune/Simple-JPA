/**
 * Copyright (c) Nicolas Seyvet, 2010.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * Nicolas Seyvet MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Baboune
 * Date: 13-Nov-2010
 * Time: 15:36:32
 */
package data;

import data.persist.Company;
import data.persist.Employee;
import loader.TimeInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * A relatively simple Data Access Object exposed as Stateless Bean.
 *
 * Used to CRUD the entities: {@link Employee}, {@link Company}.
 */
@Stateless
@Interceptors(TimeInterceptor.class)
public class Dao implements LocalSBDao {

    @PersistenceContext(unitName = "BM")
    private EntityManager em = null;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long createCompany(final String name, final int nb) throws ConstraintException
    {
         if(!isUnique(name)) {
            throw new ConstraintException("Name already in use");
        }
        Company bc = new Company();
        bc.setName(name);
        Employee se = null;
        String time = String.valueOf(System.currentTimeMillis());
        for (int i = 0; i < nb; i++) {
            se = new Employee();
            se.setName(time + "::" + i);
            se.setCompany(bc);
            bc.getEmployees().add(se);
        }
        em.persist(bc);
        return bc.getId();
    }

    public int delete(Long id) {
        Company bc = em.find(Company.class, id);
        if(bc == null) {
            return 0;
        }
        int count = 0;
        for (Employee se:bc.getEmployees()) {
            em.remove(se);
            count++;
        }
        em.remove(bc);
        return ++count;
        /*Query q = em.createQuery("DELETE FROM Employee se WHERE se.companyId = :id");
        q.setParameter("id", id);
        q.executeUpdate();
        q = em.createQuery("DELETE FROM Company c WHERE c.id = :id");
        q.setParameter("id", id);
        return q.executeUpdate();
        */
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int updateWithEmployees(int nb, Long cid) {
        Employee se = null;
        String time = String.valueOf(System.currentTimeMillis());
        Company persisted = em.find(Company.class, cid);
        for (int i = 0; i < nb; i++) {
            se = new Employee();
            se.setName(time + "::" + i);
            se.setCompany(persisted);
            persisted.getEmployees().add(se);
        }
        return nb;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public <T> T find(Class<T> clazz, Long l) {
        return em.find(clazz, l);
    }

    @Override
    public Company findCompanyByName(String name) {
        try {
            return (Company) em.createNamedQuery("BigCompany.findByName").setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Company updateCompanyName(Long id, String name) throws ConstraintException {
        if(!isUnique(name)) {
            throw new ConstraintException("Name already in use");
        }
        Company bc =  em.find(Company.class, id);
        bc.setName(name);
        return bc;
    }


    private boolean isUnique(final String name) {
        Query q = em.createQuery("SELECT COUNT(c.id) FROM Company c WHERE c.name = :name");
        q.setParameter("name", name);
        Number nb = (Number)q.getSingleResult();
        return (nb.intValue() == 0);
    }
}
