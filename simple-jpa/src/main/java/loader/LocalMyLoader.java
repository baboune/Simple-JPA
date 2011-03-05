package loader;

import javax.ejb.Local;

/**
 * Copyright (c) Nicolas Seyvet, 2010.
 * <p/>
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 * <p/>
 * Nicolas Seyvet MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * <p/>
 * User: Baboune
 * Date: 22-Nov-2010
 * Time: 21:14:44
 */
@Local
public interface LocalMyLoader {
    float createACompanyWithEmployee(final String name,
                                     final int nbCie,
                                     final int employees) throws ConstraintException;


    public float deleteCompany(final int nb);


    public float addEmployees(final int nb, final int nbEmployees);


    public float getFullCompany(final int nb);


    public float getSmallEmployees(final int start, final int max);
}
