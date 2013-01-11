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

import data.ConstraintException;
import data.LocalSBDao;
import data.persist.Company;
import loader.vo.CompanyVo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
@WebService
public class MyLoader implements LocalMyLoader {
    static final Logger LOG = Logger.getLogger("MyLoader");

    @PersistenceContext(unitName = "BM")
    private EntityManager em = null;

    @EJB
    LocalSBDao dao = null;

    @WebMethod()
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public float createACompanyWithEmployee(@WebParam(name = "name") final String name,
                                            @WebParam(name = "nbOfCies") final int nbCie,
                                            @WebParam(name = "nbOfEmployeess") final int employees)
            throws ConstraintException {
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


    @WebMethod()
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public float getFullCompany(@WebParam(name = "nb") final int nb) {
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
            Company bc = dao.find(Company.class, l);
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

    @WebMethod()
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CompanyVo getCompany(@WebParam(name = "id") final Long id) {
        LOG.info("get company :" + id);
        Company bc = dao.find(Company.class, id);
        return CompanyVo.toCompanyVo(bc);
    }

    @WebMethod()
    public CompanyVo getCompanyByName(@WebParam(name = "name") final String name) {
        LOG.info("get company :" + name);
        Company bc = dao.findCompanyByName(name);
        return CompanyVo.toCompanyVo(bc);
    }

    @WebMethod()
    public CompanyVo updateCompanyName(@WebParam(name = "id") final Long id,
                                       @WebParam(name = "name") final String name) throws ConstraintException {
        LOG.info("update company :" + id);
        Company bc = dao.updateCompanyName(id, name);
        return CompanyVo.toCompanyVo(bc);
    }

    @WebMethod()
    public boolean deleteCompany(@WebParam(name = "id") final Long id) {
        LOG.info("delete company :" + id);
        return dao.delete(id) != 0;
    }

    @WebMethod()
    public boolean changeAndRollback(@WebParam(name = "companyId") final Long id) {
        LOG.info("update and rollback company :" + id);
        try{
            dao.updateCompanyName(id, "a name that can not be persisted cause it will be rolled back");
        } catch (ConstraintException e) {
            return false;
        }
        // Force rollback with an exception.
        throw new IllegalArgumentException("Trigger rollback with runtime exception.");
    }
}
