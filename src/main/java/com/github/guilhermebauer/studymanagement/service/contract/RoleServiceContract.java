package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.RoleAllReadyRegisterException;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.exception.RoleNotFoundException;

/**
 * Service interface for managing roles within the application.
 *
 * <p>This service provides methods to create and retrieve roles based on specific
 * criteria, such as the role's name.
 */

public interface RoleServiceContract {

    /**
     * Creates a new role in the system.
     *
     * <p>This method takes a {@link RoleVO} object containing the role details
     * and saves it to the database. The returned {@link RoleVO} object will
     * contain any additional information generated during creation, such as
     * a unique role identifier.
     *
     * @param role The {@link RoleVO} object representing the role to be created,
     *             including role name and any additional details.
     * @return {@link RoleVO} object containing the details of the newly created role.
     * @throws FieldNotFound if the role data is null or empty.
     * @throws RoleAllReadyRegisterException if the role all ready register into database preventing duplicated value.
     * @see RoleEntity
     * @see RoleVO
     */

    RoleVO createRole(RoleVO role);

    /**
     * Finds a role in the system by its name.
     *
     * <p>This method searches for a role with the specified name. If a matching
     * role is found, it is returned; otherwise, it may return null or throw
     * an exception depending on implementation.
     *
     * @param name The name of the role to search for.
     * @return {@link RoleVO} object containing the details of the found role,
     *         or null if no matching role is found.
     * @throws RoleNotFoundException no role with the specified name is found
     *                               (optional based on implementation).
     * @see RoleEntity
     * @see RoleVO
     */

    RoleVO findRoleByName(String name);


}
