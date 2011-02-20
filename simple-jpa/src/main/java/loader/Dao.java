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
package loader;

import persist.BigCompany;
import persist.SmallEmployee;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 */
@Stateless
@Interceptors( TimeInterceptor.class )
public class Dao implements LocalDao {

    @PersistenceContext(unitName = "BM")
    private EntityManager em = null;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long createCompany(final String name, final int nb) {
        BigCompany bc = new BigCompany();
        bc.setName(name);
        SmallEmployee se = null;
        String time = String.valueOf(System.currentTimeMillis());
        for (int i = 0; i < nb; i++) {
            se = new SmallEmployee();
            se.setName(time + "::" + i);
            se.setCompany(bc);
            bc.getEmployees().add(se);
        }
        em.persist(bc);
        return bc.getId();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int delete(Long id) {
        Query q = em.createQuery("DELETE FROM SmallEmployee se WHERE se.company.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();
        q = em.createQuery("DELETE FROM BigCompany c WHERE c.id = :id");
        q.setParameter("id", id);
        return q.executeUpdate();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int updateWithEmployees(int nb, Long cid) {
        SmallEmployee se = null;
        String time = String.valueOf(System.currentTimeMillis());
        BigCompany persisted = em.find(BigCompany.class, cid);
        for (int i = 0; i < nb; i++) {
            se = new SmallEmployee();
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
}
