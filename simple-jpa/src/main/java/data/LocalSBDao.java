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
 * Time: 15:36:10
 */
package data;

import data.persist.Company;

import javax.ejb.Local;

/**
 *
 *
 */
@Local 
public interface LocalSBDao {
    public int updateWithEmployees(int nb, Long cid);

    public int delete(Long id);

    public Long createCompany(final String name, final int nb) throws ConstraintException;

    <T> T find(Class<T> bigCompanyClass, Long l);

    Company findCompanyByName(String name);

    Company updateCompanyName(Long id, String name) throws ConstraintException;
}
