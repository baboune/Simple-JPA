/**
 /**
 * Copyright (c) Ericsson AB, 2007.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * ERICSSON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * <p/>
 * User: Baboune
 * Date: 3-May-2009
 * Time: 6:54:37 PM
 *
 */
package loader;

import persist.BigCompany;
import persist.SmallEmployee;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO Add comments (class description)
 *
 * @author Baboune
 * @since 3-May-2009
 */
@Stateless
public class MyLoader implements LocalMyLoader {
    static final Logger LOG = Logger.getLogger("MyLoader");

    @PersistenceContext(unitName = "BM")
    private EntityManager em = null;

    @EJB
    LocalDao dao = null;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public float createACompanyWithEmployee(final String name,
                                            final int nbCie,
                                            final int employees) {
        Long[] ids = new Long[nbCie];
        long time[] = new long[nbCie];
        for (int i = 0; i < nbCie; i++) {
            time[i] = System.nanoTime();
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.finest("createACompanyWithEmployee start: " + time[i]);
            }
            ids[i] = dao.createCompany(name + i, employees);
            time[i] = System.nanoTime() - time[i];
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.fine("createACompanyWithEmployee end: " + time[i]);
            }
        }
        long sum = 0;
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("createACompanyWithEmployee times: " + Arrays.toString(time));
        }
        for (long l : time) {
            sum += l;
        }
        return sum / 1000000f / (float) nbCie;
    }


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public float deleteCompany(final int nb) {
        Query q = em.createQuery("SELECT bc.id FROM BigCompany bc");
        q.setMaxResults(nb);
        List<Long> ids = q.getResultList();

        long time[] = new long[nb];
        int i = 0;
        for (Long id : ids) {
            time[i] = System.nanoTime();
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.finest("deleteCompany start: " + time[i]);
            }
            dao.delete(id);
            time[i] = System.nanoTime() - time[i];
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.finest("deleteCompany end: " + time[i]);
            }
            i++;
        }
        long sum = 0;
        for (long l : time) {
            sum += l;
        }
        return sum / 1000000f / (float) nb;
    }


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public float addEmployees(final int nb,
                              final int nbEmployees) {
        String query = "SELECT ID FROM BIGCOMPANY ORDER BY RAND() LIMIT 1";
        List<Long> cids = new ArrayList<Long>(nb);
        for (int i = 0; i < nb; i++) {
            Query q = em.createNativeQuery(query);
            cids.add((Long) q.getSingleResult());
        }

        long[] time = new long[nb];
        int i = 0;
        for(long cid: cids) {
            time[i] = System.nanoTime();
            dao.updateWithEmployees(nbEmployees, cid);
            time[i] = System.nanoTime() - time[i];
            i++;
        }
        long sum = 0;
        for (long l : time) {
            sum += l;
        }
        return sum / 1000000f / nb;
    }


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public float getFullCompany(final int nb) {
        String query = "SELECT ID FROM BIGCOMPANY ORDER BY RAND() LIMIT 1";
        List<Long> cids = new ArrayList<Long>(nb);
        for (int i = 0; i < nb; i++) {
            Query q = em.createNativeQuery(query);
            cids.add((Long) q.getSingleResult());
        }

        String[] res = new String[cids.size()];
        int count = 0;
        long time[] = new long[cids.size()];
        for (Long l : cids) {
            time[count] = System.nanoTime();
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.finest("getFullCompany start: " + time[count]);
            }
            BigCompany bc = dao.find(BigCompany.class, l);
            time[count] = System.nanoTime() - time[count];
            if (LOG.isLoggable(Level.FINEST)) {
                LOG.finest("getFullCompany end: " + time[count]);
            }
            if (bc != null) {
                res[count] = bc.toString();
            } else {
                res[count] = "";
            }
            count++;
        }
        long sum = 0;
        for (long l : time) {
            sum += l;
        }
        System.out.println("get full cie took: " + ((float) (sum)) / (res.length) + " ns");
        return sum / 1000000f / (float) count;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public float getSmallEmployees(final int start,
                                   final int max) {
        String query = "SELECT ID FROM SMALLEMPLOYEE ORDER BY RAND() LIMIT 1";
        List<Long> ids = new ArrayList<Long>(max);
        for (int i = 0; i < max; i++) {
            Query q = em.createNativeQuery(query);
            ids.add((Long) q.getSingleResult());
        }

        long time = System.nanoTime();
        if (LOG.isLoggable(Level.FINEST)) {
            LOG.finest("getSmallEmployees start: " + time);
        }
        for (Number id : ids) {
            SmallEmployee se = dao.find(SmallEmployee.class, id.longValue());
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("se: " + se);
            }
        }
        long end = System.nanoTime();
        if (LOG.isLoggable(Level.FINEST)) {
            LOG.finest("getSmallEmployees end: " + end);
        }
        System.out.println("get small employees took: " + ((float) (end - time)) / (max) + " ns");
        return (end - time) / 1000000f / (float) max;

    }
}
